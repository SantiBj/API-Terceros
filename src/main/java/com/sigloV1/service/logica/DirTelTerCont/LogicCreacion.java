package com.sigloV1.service.logica.DirTelTerCont;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomDireccion;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomTelefono;
import com.sigloV1.service.interfaces.direccionesTelefonos.IDireccionService;
import com.sigloV1.service.interfaces.direccionesTelefonos.ITelefonoService;
import com.sigloV1.service.logica.DirTelTerCont.Params.RelacionarALL;
import com.sigloV1.service.logica.DirTelTerCont.Params.TelefonoParams;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionIdTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


//TODO ----> las direcciones y telefonos usados en el rol contacto solo se usaran hay
@Component
public class LogicCreacion {

    @Autowired
    private DirTelTerRepository dirTelTerRepository;

    @Autowired
    private IDireccionService direccionService;

    @Autowired
    private ITelefonoService telefonoService;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private DirTelTerConRepository relacionesContactoRep;


    public void asociarDatosConContacto(Long datosId, Long contactoId){
        ContactoEntity contacto = contactoRepository.findById(contactoId).orElseThrow(
                () -> new BadRequestCustom("El contacto no existe"));

        DirTelTerEntity relacionDirTelTer = dirTelTerRepository.findById(datosId).orElseThrow(
                () -> new BadRequestCustom("La relacion entre los datos de contacto no existe"));

        relacionesContactoRep
                .findByDireccionTelefonoAndContacto(relacionDirTelTer, contacto)
                .orElseGet(() -> (
                        relacionesContactoRep.save(
                                DirTelTerContEntity.builder()
                                        .direccionTelefono(relacionDirTelTer)
                                        .contacto(contacto)
                                        .build()
                        )
                ));
    }

    public void crearDireccionAsociarTercero(DireccionReqDTO data){
        ReturnCustomDireccion direccionNueva = direccionService.crearDireccion(data);
        TerceroEntity terceroEntity = terceroAdapter.obtenerTerceroOException(data.getTerceroId());

        if (direccionNueva.getAlReadyExisted()) {
            Optional<DirTelTerEntity> relacion = dirTelTerRepository.findByTerceroAndDireccionAndUsadaEnContacto(
                    terceroEntity, direccionNueva.getDireccion(), data.getContactoId() != null
            );

            if (relacion.isPresent()) {
                throw new BadRequestCustom("La relacion entre la direccion y el tercero ya existe.");
            }
        }

        DirTelTerEntity relaciones = dirTelTerRepository.save(
                DirTelTerEntity.builder()
                        .direccion(direccionNueva.getDireccion())
                        .tercero(terceroEntity)
                        .estadoDireccion(true)
                        .usadaEnContacto(data.getContactoId() != null)
                        .build()
        );
        if (data.getContactoId() != null){
            System.out.println("relacionando contacto");
            asociarDatosConContacto(relaciones.getId(),data.getContactoId());
        }
    }

    public void crearTelefonoAsociarTercero(TelefonoReqDTO data){

        ReturnCustomTelefono telefonoNuevo = telefonoService.crearTelefono(data);
        TerceroEntity terceroEntity = terceroAdapter.obtenerTerceroOException(data.getTerceroId());


        if (telefonoNuevo.getAlReadyExisted()) {
            Optional<DirTelTerEntity> relacion = dirTelTerRepository.findByTerceroAndTelefonoAndUsadaEnContacto(
                    terceroEntity,telefonoNuevo.getTelefono(),data.getContactoId() != null
            );
            if (relacion.isPresent()) {
                throw new BadRequestCustom("La relacion entre el telefono y el tercero ya existe");
            }
        }

        DirTelTerEntity relaciones = dirTelTerRepository.save(
                DirTelTerEntity.builder()
                        .telefono(telefonoNuevo.getTelefono())
                        .tercero(terceroEntity)
                        .estadoTelefono(true)
                        .extension(data.getExtension())
                        .usadaEnContacto(data.getContactoId() != null)
                        .build()
        );

        if (data.getContactoId() != null){
            asociarDatosConContacto(relaciones.getId(),data.getContactoId());
        }
    }

    public void crearTelefonosAsociarNuevaDireccion(DireccionTelefonosReqDTO dataDireccion) {

        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(dataDireccion.getTerceroId());

        ReturnCustomDireccion direccion = direccionService.crearDireccion(DireccionReqDTO
                .builder()
                .direccion(dataDireccion.getDireccion())
                .nombre(dataDireccion.getNombre())
                .codigoPostal(dataDireccion.getCodigoPostal())
                .ciudad(dataDireccion.getCiudad())
                .build());

        List<TelefonoParams> telefonos = dataDireccion.getTelefonos().stream().map(t ->
                TelefonoParams.builder()
                        .telefono(telefonoService.crearTelefono(t).getTelefono())
                        .extension(t.getExtension())
                        .build()
        ).toList();

        DireccionTelefonosResDTO relaciones = relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion.getDireccion())
                .telefonos(telefonos)
                .usadaComoContacto(dataDireccion.getContactoId())
                .tercero(tercero)
                .build());

        if (dataDireccion.getContactoId() != null){
            relaciones.getTelefonos().forEach(t->{
                asociarDatosConContacto(t.getIdRelacionTer(),dataDireccion.getContactoId());
            });
        }
    }

    public void crearTelefonosAsociarDireccionExistente(DireccionIdTelefonosReqDTO data) {
        System.out.println("pasando por direccion id");

        TerceroEntity tercero = terceroAdapter
                .obtenerTerceroOException(data.getIdTercero());

        DireccionEntity direccion = direccionService
                .obtenerDireccionOException(data.getDireccionId());

        List<TelefonoParams> telefonos = data.getTelefonos().stream().map(t ->
                TelefonoParams.builder()
                        .telefono(telefonoService.crearTelefono(t).getTelefono())
                        .extension(t.getExtension())
                        .build()
        ).toList();

        DireccionTelefonosResDTO relaciones = relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion)
                .telefonos(telefonos)
                .usadaComoContacto(data.getContactoId())
                .tercero(tercero)
                .build());

        if (data.getContactoId() != null){
            relaciones.getTelefonos().forEach(t->{
                asociarDatosConContacto(t.getIdRelacionTer(), data.getContactoId());
            });
        }
    }


    //funcion de relacion


    public DireccionTelefonosResDTO relacionarDireccionAndTelefonosAndTercero(RelacionarALL data){


        Boolean isContacto = data.getUsadaComoContacto() != null;

        List<DirTelTerEntity> relaciones = data.getTelefonos().stream()
                .map(t->{
                    DirTelTerEntity relacionPreviaDireccion = dirTelTerRepository
                            .findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto(
                                    data.getTercero(),t.getTelefono(),data.getDireccion(),isContacto
                            );

                    if (relacionPreviaDireccion != null){
                        return relacionPreviaDireccion;
                    }


                    DirTelTerEntity relacionPreviaTercero = dirTelTerRepository
                            .findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto(
                            data.getTercero(),t.getTelefono(),null,isContacto
                    );


                    if (relacionPreviaTercero != null){
                        relacionPreviaTercero.setDireccion(data.getDireccion());
                        relacionPreviaTercero.setEstadoDireccion(true);
                        return dirTelTerRepository.save(relacionPreviaTercero);
                    } else{
                        return dirTelTerRepository.save(
                                DirTelTerEntity.builder()
                                        .tercero(data.getTercero())
                                        .telefono(t.getTelefono())
                                        .direccion(data.getDireccion())
                                        .estadoDireccion(true)
                                        .estadoTelefono(true)
                                        .extension(t.getExtension())
                                        .usadaEnContacto(isContacto)
                                        .build()
                        );
                    }
                }).toList();


        if (isContacto){
            ContactoEntity contacto = contactoRepository.findById(data.getUsadaComoContacto()).orElseThrow(
                    () -> new BadRequestCustom("El contacto no existe"));

            relaciones.forEach(r->{
                relacionesContactoRep
                        .findByDireccionTelefonoAndContacto(r, contacto)
                        .orElseGet(() -> (
                                relacionesContactoRep.save(
                                        DirTelTerContEntity.builder()
                                                .direccionTelefono(r)
                                                .contacto(contacto)
                                                .build()
                                )
                        ));
            });
        }

        DirTelTerEntity relacionDireccionTer = dirTelTerRepository
                .findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto
                        (data.getTercero(),null,data.getDireccion()
                        ,isContacto);


        if (isContacto && relacionDireccionTer != null) {

        }

        if (relacionDireccionTer != null){
            System.out.println();
            dirTelTerRepository.delete(relacionDireccionTer);
        }


        return DireccionTelefonosResDTO.builder()
                .id(data.getDireccion().getId())
                .nombre(data.getDireccion().getNombre())
                .direccion(data.getDireccion().getDireccion())
                .codigoPostal(data.getDireccion().getCodigoPostal())
                .ciudad(data.getDireccion().getCiudad().getId())
                .telefonos(relaciones.stream().map(t->
                        TelefonoResDTO.builder()
                                .id(t.getTelefono().getId())
                                .idRelacionTer(t.getId())
                                .tipo(t.getTelefono().getTipoTelefono())
                                .numero(t.getTelefono().getNumero())
                                .estado(t.getEstadoTelefono())
                                .extension(t.getExtension())
                                .build()
                        ).toList())
                .estado(true)
                .build();

    }

}
