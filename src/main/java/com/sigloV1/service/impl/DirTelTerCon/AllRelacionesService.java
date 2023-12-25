package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.DirTelTerContEntity;
import com.sigloV1.dao.models.DirTelTerEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.service.logica.DirTelTerCont.LogicCreacion;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.web.dtos.req.DirTelTerCon.*;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionesAndTelefonosDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AllRelacionesService implements IAllRelacionesService {

    @Autowired
    private DirTelTerRepository dirTelTerRepository;

    @Autowired
    private LogicCreacion logicCreacion;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private DirTelTerRepository relacionesDirTelTerRep;

    @Autowired
    private MetodosContacto metodosContacto;

    @Autowired
    private DirTelTerConRepository relacionesContacto;



    @Override
    public List<DireccionTelefonosResDTO> direccionesTercero(Long idTercero) {
        return null;
    }

    @Override
    public List<DireccionTelefonosResDTO> dirTelRolContacto(Long idTercero) {
        return null;
    }

    @Override
    public List<TelefonoResDTO> telefonosSinDireccionTercero(Long idTercero) {
        return null;
    }

    @Override
    public void estadoDatosTercero(EDato dato, Long relacionId) {

    }

    public void crearDatosDeContacto(DatosDeContactoDTO datos){
        if (datos.getDireccion() != null){
            logicCreacion.crearDireccionAsociarTercero(datos.getDireccion());
        }else if(datos.getTelefono() != null){
            logicCreacion.crearTelefonoAsociarTercero(datos.getTelefono());
        }else if(datos.getDireccionTelefonos() != null){
            logicCreacion.crearTelefonosAsociarNuevaDireccion(datos.getDireccionTelefonos());
        }else if(datos.getDireccionIdTelefonos() != null){
            logicCreacion.crearTelefonosAsociarDireccionExistente(datos.getDireccionIdTelefonos());
        }
    }

    public DireccionesAndTelefonosDTO direccionTelefonosTercero(Long terceroId){
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(terceroId);

        List<DirTelTerEntity> datos = relacionesDirTelTerRep
                .findByTerceroAndUsadaEnContacto(tercero,false);

        List<DireccionTelefonosResDTO> direccionesTelefonos = new ArrayList<>();
        List<TelefonoResDTO> telefonos = new ArrayList<>();

        datos.forEach(dirTel->{
            if (dirTel.getDireccion() != null){
                direccionesTelefonos.add(DireccionTelefonosResDTO.builder()
                                    .id(dirTel.getDireccion().getId())
                                    .relacionId(datos.size() == 1 ? dirTel.getId() : null )
                                    .nombre(dirTel.getNombreDireccion())
                                    .direccion(dirTel.getDireccion().getDireccion())
                                    .codigoPostal(dirTel.getDireccion().getCodigoPostal())
                                    .ciudad(dirTel.getDireccion().getCiudad().getNombre())
                                    .telefonos(datos.stream()
                                            .filter(tel->Objects.equals(tel.getDireccion().getId(),dirTel.getDireccion().getId()))
                                            .map(tel->{
                                                datos.remove(tel);
                                                return TelefonoResDTO.builder()
                                                        .id(tel.getTelefono().getId())
                                                        .idRelacionTer(tel.getId())
                                                        .tipo(tel.getTelefono().getTipoTelefono())
                                                        .numero(tel.getTelefono().getNumero())
                                                        .estado(tel.getEstadoTelefono())
                                                        .extension(tel.getExtension())
                                                        .build();
                                            }).toList()
                                    )
                                    .estado(dirTel.getEstadoDireccion())
                            .build());

            }else{
                telefonos.add(TelefonoResDTO.builder()
                                .id(dirTel.getTelefono().getId())
                                .idRelacionTer(dirTel.getId())
                                .tipo(dirTel.getTelefono().getTipoTelefono())
                                .numero(dirTel.getTelefono().getNumero())
                                .estado(dirTel.getEstadoTelefono())
                                .extension(dirTel.getExtension())
                        .build());
            }
        });

        return DireccionesAndTelefonosDTO.builder()
                .direcciones(direccionesTelefonos)
                .telefonos(telefonos)
                .build();
    }


    public DireccionesAndTelefonosDTO direccionesTelefonosContacto(Long contactoId){
        ContactoEntity contacto = metodosContacto.obtenerContactoOException(contactoId);

        List<DirTelTerContEntity> datosContacto = relacionesContacto.findByContacto(contacto);

        List<DireccionTelefonosResDTO> direccionTelefonos = new ArrayList<>();
        List<TelefonoResDTO> telefonos = new ArrayList<>();

        datosContacto.forEach(dc->{
            if (dc.getDireccionTelefono().getDireccion() != null){
                direccionTelefonos.add(DireccionTelefonosResDTO.builder()
                                .id(dc.getDireccionTelefono().getDireccion().getId())
                                .relacionId(datosContacto.size() == 1 ? dc.getId() : null)
                                .nombre(dc.getNombreDireccion())
                                .direccion(dc.getDireccionTelefono().getDireccion().getDireccion())
                                .codigoPostal(dc.getDireccionTelefono().getDireccion().getCodigoPostal())
                                .ciudad(dc.getDireccionTelefono().getDireccion().getCiudad().getNombre())
                                .telefonos(datosContacto.stream()
                                        .filter(tel->Objects.equals(tel.getDireccionTelefono().getDireccion().getId(),dc.getDireccionTelefono().getDireccion().getId()))
                                        .map(tel-> {
                                            datosContacto.remove(tel);
                                            return TelefonoResDTO.builder()
                                                    .id(tel.getDireccionTelefono().getTelefono().getId())
                                                    .idRelacionTer(tel.getId())
                                                    .tipo(tel.getDireccionTelefono().getTelefono().getTipoTelefono())
                                                    .numero(tel.getDireccionTelefono().getTelefono().getNumero())
                                                    .estado(tel.getEstadoTelefono())
                                                    .extension(tel.getDireccionTelefono().getExtension())
                                                    .build();
                                        }).toList())
                                .estado(dc.getEstadoDireccion())
                                .build());
            }else{
               telefonos.add(TelefonoResDTO.builder()
                               .id(dc.getDireccionTelefono().getTelefono().getId())
                               .idRelacionTer(dc.getId())
                               .tipo(dc.getDireccionTelefono().getTelefono().getTipoTelefono())
                               .numero(dc.getDireccionTelefono().getTelefono().getNumero())
                               .estado(dc.getEstadoTelefono())
                               .extension(dc.getDireccionTelefono().getExtension())
                       .build());

            }
        });

        return DireccionesAndTelefonosDTO.builder()
                .telefonos(telefonos)
                .direcciones(direccionTelefonos)
                .build();
    }


    //de un tercero en su rol como tercero

    //desactivar el telefono o direccion de una relacion con contacto

    //activar el telefono o direccion de una relacion con contacto

    //desactivar el telefono o direccion de una relacion con tercero

    //activar el telefono o direccion de una relacion con tercero

    //eliminar relacion de un contacto con una direccion o contacto de un tercero en su rol como contacto

    //eliminar relacion de un tercero con una direccion o contacto

}
