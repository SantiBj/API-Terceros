package com.sigloV1.service.logica.DirTelTerCont;

import com.sigloV1.dao.models.DirTelTerEntity;
import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.direccionesTelefonos.IDireccionService;
import com.sigloV1.service.interfaces.direccionesTelefonos.ITelefonoService;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


//TODO ----> las direcciones y telefonos usados en el rol contacto solo se usaran hay
@Component
public class Logic {

    @Autowired
    private DirTelTerRepository dirTelTerRepository;

    @Autowired
    private IDireccionService direccionService;

    @Autowired
    private ITelefonoService telefonoService;

    @Autowired
    private TerceroAdapter terceroAdapter;

    //validar que no exista
    public DireccionResDTO crearDireccionAndRelacionar(DireccionReqDTO direccion,Long tercero,Boolean isContacto){
        DirTelTerEntity relaciones = dirTelTerRepository.save(
                DirTelTerEntity.builder()
                        .direccion(direccionService.crearDireccion(direccion))
                        .tercero(terceroAdapter.obtenerTerceroOException(tercero))
                        .estadoDireccion(true)
                        .usadaEnContacto(isContacto)
                        .build()
        );

        return DireccionResDTO.builder()
                .id(relaciones.getDireccion().getId())
                .idRelacionTer(relaciones.getId())
                .nombre(relaciones.getDireccion().getNombre())
                .direccion(relaciones.getDireccion().getDireccion())
                .codigoPostal(relaciones.getDireccion().getCodigoPostal())
                .ciudad(relaciones.getDireccion().getCiudad().getId())
                .estado(relaciones.getEstadoDireccion())
                .build();
    }

    //validar que no exista
    public TelefonoResDTO crearTelefonoAndRelacionar(TelefonoReqDTO telefono,Long tercero,Boolean isContacto){
        DirTelTerEntity relaciones = dirTelTerRepository.save(
                DirTelTerEntity.builder()
                        .telefono(telefonoService.crearTelefono(telefono))
                        .tercero(terceroAdapter.obtenerTerceroOException(tercero))
                        .estadoTelefono(true)
                        .usadaEnContacto(isContacto)
                        .build()
        );

        return TelefonoResDTO.builder()
                .id(relaciones.getTelefono().getId())
                .idRelacionTer(relaciones.getId())
                .tipo(relaciones.getTelefono().getTipoTelefono())
                .numero(relaciones.getTelefono().getNumero())
                .estado(relaciones.getEstadoTelefono())
                .build();
    }

    private DirTelTerEntity crearTelefonoAsociarConDireccion(TelefonoReqDTO telefono,DireccionEntity direccion
            ,TerceroEntity tercero){

        TelefonoEntity telefonoEntity = telefonoService.crearTelefono(telefono);

        Optional<DirTelTerEntity> relacion = dirTelTerRepository
                .findByTerceroAndTelefonoAndUsadaEnContacto(tercero,telefonoEntity,false);

        if (relacion.isPresent()){
            relacion.get().setDireccion(direccion);
            return dirTelTerRepository.save(relacion.get());
        }else{
            return  dirTelTerRepository.save(
                    DirTelTerEntity.builder()
                            .telefono(telefonoEntity)
                            .estadoTelefono(true)
                            .direccion(direccion)
                            .estadoDireccion(true)
                            .extension(telefono.getExtension())
                            .usadaEnContacto(false)
                            .build()
            );
        }
    }

    //TODO como funcionaria añadir el telefono por unidad a una direccion y viceversa
    //TODO es solo se usa en la creacion de direcciones para tercero no para su rol contacto
    public DireccionTelefonosResDTO crearDireccionAndTelefonosAndRelacionar(
            DireccionTelefonosReqDTO direccionTelefonos, Long tercero ){

        TerceroEntity terceroEntity = terceroAdapter.obtenerTerceroOException(tercero);

        DireccionEntity direccion = direccionService.crearDireccion(
                DireccionReqDTO.builder()
                        .nombre(direccionTelefonos.getNombre())
                        .direccion(direccionTelefonos.getDireccion())
                        .codigoPostal(direccionTelefonos.getCodigoPostal())
                        .ciudad(direccionTelefonos.getCiudad())
                        .build()
        );

        List<DirTelTerEntity> relaciones = direccionTelefonos.getTelefonos().stream()
                .map(t->crearTelefonoAsociarConDireccion(t,direccion,terceroEntity))
                .toList();

        dirTelTerRepository.findByTerceroAndDireccionAndUsadaEnContacto(terceroEntity,direccion,false)
                .ifPresent(value->dirTelTerRepository.delete(value));

        return DireccionTelefonosResDTO.builder()
                .id(direccion.getId())
                .idRelacionTer(relaciones.get(0).getId())
                .nombre(direccion.getNombre())
                .direccion(direccion.getDireccion())
                .codigoPostal(direccion.getCodigoPostal())
                .ciudad(direccion.getCiudad().getId())
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
