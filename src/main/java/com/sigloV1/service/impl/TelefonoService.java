package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.DireccionTelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroTelefonoRepository;
import com.sigloV1.service.interfaces.ITelefonoService;
import com.sigloV1.service.interfaces.adapters.direccion.DireccionAdapter;
import com.sigloV1.service.interfaces.adapters.telefono.TelefonoAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService {

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private TerceroTelefonoRepository terceroTelefonoRepository;

    @Autowired
    private DireccionTelefonoRepository direccionTelefonoRepository;

    @Autowired
    private TelefonoAdapter logShareTel;

    @Autowired
    private DireccionAdapter direccionAdapter;



    private TelefonoEntity obtenerTelefonoOException(Long id) {
        return telefonoRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("El telefono no existe"));
    }

    @Transactional
    public List<TelefonoResDTO> crearTelefonosTercero(List<TelefonoReqDTO> telefonos, Long tercero) {
        return telefonos.stream().map(telefono -> {
            TerceroTelefonoEntity relacion = logShareTel.crearTelefonoUnionTercero(telefono, tercero);
            return TelefonoResDTO.builder()
                    .id(relacion.getTelefono().getId())
                    .numero(relacion.getTelefono().getNumero())
                    .tipoTelefono(relacion.getTelefono().getTipoTelefono())
                    .extension(relacion.getTelefono().getExtension())
                    .estado(relacion.getEstado())
                    .build();
        }).toList();
    }


    @Override
    public Boolean estadoTelefonoTercero(Long idTelefono, Long idTercero, Boolean estado) {
        try {
            TelefonoEntity t = obtenerTelefonoOException(idTelefono);

            TerceroTelefonoEntity telefonoUpdate = terceroTelefonoRepository.findByTelefonoAndTercero(t
                            , terceroAdapter.obtenerTerceroOException(idTercero))
                    .orElseThrow(() -> new BadRequestCustom("El telefono no se encuentra relacionado."));

            telefonoUpdate.setEstado(estado);
            terceroTelefonoRepository.save(telefonoUpdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public TelefonoResDTO editarTelefono(Long idTelefono, Long idTercero, TelefonoReqDTO datos) {
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(idTercero);
        TelefonoEntity telefono = obtenerTelefonoOException(idTelefono);
        telefono.setNumero(datos.getNumero());
        telefono.setTipoTelefono(datos.getTipoTelefono());
        telefono.setExtension(datos.getExtension());
        telefono = telefonoRepository.save(telefono);
        return TelefonoResDTO.builder()
                .id(telefono.getId())
                .numero(telefono.getNumero())
                .tipoTelefono(telefono.getTipoTelefono())
                .extension(telefono.getExtension())
                .estado(terceroTelefonoRepository.findByTelefonoAndTercero(telefono, tercero).get().getEstado())
                .build();
    }

    @Override
    public List<TelefonoResDTO> obtenerTelefonosTercero(Long idTercero) {
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(idTercero);
        List<TerceroTelefonoEntity> relaciones = terceroTelefonoRepository.findByTercero(tercero);
        return relaciones.stream().map(relacion -> {
            return TelefonoResDTO.builder()
                    .id(relacion.getTelefono().getId())
                    .numero(relacion.getTelefono().getNumero())
                    .tipoTelefono(relacion.getTelefono().getTipoTelefono())
                    .extension(relacion.getTelefono().getExtension())
                    .estado(relacion.getEstado())
                    .build();
        }).toList();
    }


    @Override
    public void eliminarTelefonoTercero(Long idTelefono, Long idTercero, Long idDireccion) {
        TerceroTelefonoEntity relacionTerceroTelefono = terceroTelefonoRepository
                .findByTelefonoAndTercero(
                        obtenerTelefonoOException(idTelefono),
                        terceroAdapter.obtenerTerceroOException(idTercero)
                ).orElseThrow(() -> new BadRequestCustom("El telefono no se encuentra relacionado con el tercero"));

        if (idDireccion != null) {
            TerceroDireccionEntity relacionTerceroDireccion = direccionAdapter
                    .obtenerRelacionDireccionTercero(idDireccion, relacionTerceroTelefono.getTercero());
            direccionTelefonoRepository.delete(
                    direccionTelefonoRepository.findByDireccionTerAndTelefonoTer(
                            relacionTerceroDireccion,
                            relacionTerceroTelefono
                    ).orElseThrow(() -> new BadRequestCustom("La relacion entre el telefono y la direccion no existe"))
            );
        }
        terceroTelefonoRepository.delete(relacionTerceroTelefono);
    }

}
