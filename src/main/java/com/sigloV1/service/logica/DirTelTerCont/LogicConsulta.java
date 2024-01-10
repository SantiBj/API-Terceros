package com.sigloV1.service.logica.DirTelTerCont;
import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerConRepository;
import com.sigloV1.dao.repositories.DirTelTerCon.DirTelTerRepository;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionesAndTelefonosDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.TelefonoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LogicConsulta {

    @Autowired
    private DirTelTerRepository relacionesDirTelTerRep;

    @Autowired
    private DirTelTerConRepository relacionesContacto;


    public DireccionesAndTelefonosDTO datosDeContactoTercero(TerceroEntity tercero){
        List<DirTelTerEntity> datos = relacionesDirTelTerRep
                .findByTerceroAndUsadaEnContacto(tercero,false);

        List<DireccionTelefonosResDTO> direccionesTelefonos = new ArrayList<>();
        List<TelefonoResDTO> telefonos = new ArrayList<>();

        datos.forEach(dirTel->{
            if (dirTel.getDireccion() != null){
                direccionesTelefonos.add(DireccionTelefonosResDTO.builder()
                        .id(dirTel.getDireccion().getId())
                        .relacionId(dirTel.getId())
                        .nombre(dirTel.getNombreDireccion())
                        .direccion(dirTel.getDireccion().getDireccion())
                        .codigoPostal(dirTel.getDireccion().getCodigoPostal())
                        .ciudad(dirTel.getDireccion().getCiudad().getNombre())
                        .telefonos(datos.stream()
                                .filter(tel->Objects.equals(tel.getDireccion().getId(),dirTel.getDireccion().getId()))
                                .map(tel->{
                                    datos.remove(tel);

                                    String indicativoMovil = tercero.getPais().getIndicativo();
                                    String indicativoFijo = tel.getDireccion().getCiudad().getIndicativo();

                                    return TelefonoResDTO.builder()
                                            .id(tel.getTelefono().getId())
                                            .idRelacionTer(tel.getId())
                                            .tipo(tel.getTelefono().getTipoTelefono())
                                            .numero(tel.getTelefono().getNumero())
                                            .indicativo(tel.getTelefono().getTipoTelefono() == ETipoTelefono.MOVIL ? indicativoMovil : indicativoFijo)
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
                        .indicativo(tercero.getPais().getIndicativo())
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

    public DireccionesAndTelefonosDTO obtenerDatosDeContactoContacto(ContactoEntity contacto){
        List<DirTelTerContEntity> datosContacto = relacionesContacto.findByContacto(contacto);

        List<DireccionTelefonosResDTO> direccionTelefonos = new ArrayList<>();
        List<TelefonoResDTO> telefonos = new ArrayList<>();

        datosContacto.forEach(dc->{
            if (dc.getDireccionTelefono().getDireccion() != null){
                direccionTelefonos.add(DireccionTelefonosResDTO.builder()
                        .id(dc.getDireccionTelefono().getDireccion().getId())
                        .relacionId(dc.getId())
                        .nombre(dc.getNombreDireccion())
                        .direccion(dc.getDireccionTelefono().getDireccion().getDireccion())
                        .codigoPostal(dc.getDireccionTelefono().getDireccion().getCodigoPostal())
                        .ciudad(dc.getDireccionTelefono().getDireccion().getCiudad().getNombre())
                        .telefonos(datosContacto.stream()
                                .filter(tel->Objects.equals(tel.getDireccionTelefono().getDireccion().getId(),dc.getDireccionTelefono().getDireccion().getId()))
                                .map(tel-> {
                                    String indicativoMovil = contacto.getContacto().getPais().getIndicativo();
                                    String indicativoFijo = tel.getDireccionTelefono().getDireccion().getCiudad().getIndicativo();
                                    datosContacto.remove(tel);
                                    return TelefonoResDTO.builder()
                                            .id(tel.getDireccionTelefono().getTelefono().getId())
                                            .idRelacionTer(tel.getId())
                                            .tipo(tel.getDireccionTelefono().getTelefono().getTipoTelefono())
                                            .numero(tel.getDireccionTelefono().getTelefono().getNumero())
                                            .indicativo(tel.getDireccionTelefono().getTelefono().getTipoTelefono() == ETipoTelefono.MOVIL ? indicativoMovil : indicativoFijo)
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
                        .indicativo(contacto.getContacto().getPais().getIndicativo())
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

}
