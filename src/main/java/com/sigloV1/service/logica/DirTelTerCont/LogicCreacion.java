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


    public <T> void asociarDatosConContacto(DirTelTerEntity relacion, T contactoParam){

        ContactoEntity contacto = contactoParam instanceof ContactoEntity ? (ContactoEntity) contactoParam:
                contactoRepository.findById((Long) contactoParam).orElseThrow(
                () -> new BadRequestCustom("El contacto no existe"));


        relacionesContactoRep
                .findByDireccionTelefonoAndContacto(relacion, contacto)
                .orElseGet(() -> (
                        relacionesContactoRep.save(
                                DirTelTerContEntity.builder()
                                        .direccionTelefono(relacion)
                                        .contacto(contacto)
                                        .build()
                        )
                ));
    }

    //casos en que un contacto use varias veces la misma relacion
    public void crearDireccionAsociarTercero(DireccionReqDTO data){
        ReturnCustomDireccion direccionNueva = direccionService.crearDireccion(data);
        TerceroEntity terceroEntity = terceroAdapter.obtenerTerceroOException(data.getTerceroId());

        List<DirTelTerEntity> relaciones = dirTelTerRepository.findByTerceroAndDireccionAndUsadaEnContacto(
                terceroEntity, direccionNueva.getDireccion(), data.getContactoId() != null);

        Optional<DirTelTerEntity> relacion = relaciones.stream()
                .filter(r->r.getTelefono() == null)
                .findFirst();


        if (!relaciones.isEmpty() && relacion.isPresent()){
            if (data.getContactoId() != null){
                asociarDatosConContacto(relacion.get(),data.getContactoId());
            }else{
               throw new BadRequestCustom("La relacion entre la direccion y el tercero ya existe");
            }
        }else{
            if (data.getContactoId() != null){
                asociarDatosConContacto(dirTelTerRepository.save(
                        DirTelTerEntity.builder()
                                .direccion(direccionNueva.getDireccion())
                                .tercero(terceroEntity)
                                .estadoDireccion(true)
                                .usadaEnContacto(data.getContactoId() != null)
                                .build()
                ),data.getContactoId());
            }else{
                dirTelTerRepository.save(
                        DirTelTerEntity.builder()
                                .direccion(direccionNueva.getDireccion())
                                .tercero(terceroEntity)
                                .estadoDireccion(true)
                                .usadaEnContacto(data.getContactoId() != null)
                                .build()
                );
            }
        }
    }

    //corregir este mismo
    public void crearTelefonoAsociarTercero(TelefonoReqDTO data){

        ReturnCustomTelefono telefonoNuevo = telefonoService.crearTelefono(data);
        TerceroEntity terceroEntity = terceroAdapter.obtenerTerceroOException(data.getTerceroId());

        //validar para que no me cree dos relaciones por ejemplo una direccion suelta y otra con valor

        List<DirTelTerEntity> relacion = dirTelTerRepository.findByTerceroAndTelefonoAndUsadaEnContacto(
                terceroEntity,telefonoNuevo.getTelefono(),data.getContactoId() != null
        );

        if (!relacion.isEmpty()) {
            if (data.getContactoId() != null){
                asociarDatosConContacto(relacion.get(0),data.getContactoId());
            }else{
                throw new BadRequestCustom("La relacion entre el telefono y el tercero ya existe");
            }
        }else{
            if (data.getContactoId()!= null){
                asociarDatosConContacto(DirTelTerEntity.builder()
                        .telefono(telefonoNuevo.getTelefono())
                        .tercero(terceroEntity)
                        .estadoTelefono(true)
                        .extension(data.getExtension())
                        .usadaEnContacto(data.getContactoId() != null)
                        .build(),data.getContactoId());
            }else{
                DirTelTerEntity.builder()
                        .telefono(telefonoNuevo.getTelefono())
                        .tercero(terceroEntity)
                        .estadoTelefono(true)
                        .extension(data.getExtension())
                        .usadaEnContacto(data.getContactoId() != null)
                        .build();
            }
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

        relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion.getDireccion())
                .telefonos(telefonos)
                .usadaComoContacto(dataDireccion.getContactoId())
                .tercero(tercero)
                .build());
    }

    public void crearTelefonosAsociarDireccionExistente(DireccionIdTelefonosReqDTO data) {
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

        relacionarDireccionAndTelefonosAndTercero(RelacionarALL.builder()
                .direccion(direccion)
                .telefonos(telefonos)
                .usadaComoContacto(data.getContactoId())
                .tercero(tercero)
                .build());
    }


    //funcion de relacion


    public void relacionarDireccionAndTelefonosAndTercero(RelacionarALL data){

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

                    if (relacionPreviaTercero != null && !isContacto){
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
                DirTelTerEntity relPrevTelefonoTer = dirTelTerRepository.findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto(
                        data.getTercero(),
                        r.getTelefono(),
                        null,
                        true
                );

                if (relPrevTelefonoTer != null){
                   Optional<DirTelTerContEntity> relacionTelConThisCont = relacionesContactoRep
                           .findByDireccionTelefonoAndContacto(relPrevTelefonoTer,contacto);

                   if (relacionTelConThisCont.isPresent()){
                       List<DirTelTerContEntity> contactosUsandoRelacion = relacionesContactoRep
                               .findByDireccionTelefono(relPrevTelefonoTer);

                       relacionesContactoRep.delete(relacionTelConThisCont.get());
                       if (contactosUsandoRelacion.size() == 1){
                           dirTelTerRepository.delete(relPrevTelefonoTer);
                       }
                   }
                }

                asociarDatosConContacto(r,contacto);
            });
        }

        DirTelTerEntity relacionDireccionTer = dirTelTerRepository
                .findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto
                        (data.getTercero(),null,data.getDireccion()
                        ,isContacto);


        if (relacionDireccionTer != null){
            if (isContacto){
                ContactoEntity contacto = contactoRepository.findById(data.getUsadaComoContacto()).orElseThrow(
                        () -> new BadRequestCustom("El contacto no existe"));

                Optional<DirTelTerContEntity> relacionDirConThisCont = relacionesContactoRep
                        .findByDireccionTelefonoAndContacto(relacionDireccionTer,contacto);

                if (relacionDirConThisCont.isPresent()){
                    List<DirTelTerContEntity> contUsandoRelacion = relacionesContactoRep
                            .findByDireccionTelefono(relacionDireccionTer);

                    relacionesContactoRep.delete(relacionDirConThisCont.get());
                    if (contUsandoRelacion.size() == 1){
                        dirTelTerRepository.delete(relacionDireccionTer);
                    }
                }
            }else{
                dirTelTerRepository.delete(relacionDireccionTer);
            }
        }

    }

}
