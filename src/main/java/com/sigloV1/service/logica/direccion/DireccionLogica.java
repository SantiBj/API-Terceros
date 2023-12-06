package com.sigloV1.service.logica.direccion;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.DireccionTelefonoEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroDireccionRepository;
import com.sigloV1.service.interfaces.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.adapters.direccion.DireccionAdapter;
import com.sigloV1.service.interfaces.adapters.telefono.TelefonoAdapter;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class DireccionLogica implements DireccionAdapter {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TerceroDireccionRepository terceroDireccionRepository;

    @Autowired
    private CiudadAdapter ciudadAdapter;

    @Autowired
    private TelefonoAdapter telefonoAdapter;

    @Autowired
    private ModelMapper modelMapper;

    public DireccionEntity obtenerDireccionEntityOException(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("La direccion no existe."));
    }

    public TerceroDireccionEntity obtenerRelacionDireccionTercero(Long idDireccion, TerceroEntity tercero){
        return terceroDireccionRepository.findByDireccionAndTercero(obtenerDireccionEntityOException(idDireccion),tercero)
                .orElseThrow(()->new BadRequestCustom("La relacion entre la direccion y el tercero no existe"));
    }

    private DireccionEntity creacionDireccion(DireccionReqDTO direccion) {
        Optional<DireccionEntity> direccionExistente = direccionRepository
                .findByDireccionIgnoreCase(direccion.getDireccion());
        if (direccionExistente.isEmpty()) {
            DireccionEntity newDireccion = DireccionEntity.builder()
                    .nombre(direccion.getNombre())
                    .direccion(direccion.getDireccion())
                    .codigoPostal(direccion.getCodigoPostal())
                    .ciudad(ciudadAdapter.obtenerCiudadOException(direccion.getCiudad()))
                    .build();
            return direccionRepository.save(newDireccion);
        } else {
            return direccionExistente.get();
        }
    }

    public TerceroDireccionEntity crearDireccionUnionTercero(DireccionReqDTO direccion, TerceroEntity tercero) {
        DireccionEntity direccionEntity = creacionDireccion(direccion);
        Optional<TerceroDireccionEntity> relacionExistente = terceroDireccionRepository
                .findByDireccionAndTercero(direccionEntity, tercero);
        return relacionExistente.orElseGet(() -> terceroDireccionRepository
                .save(TerceroDireccionEntity.builder()
                        .tercero(tercero)
                        .direccion(direccionEntity)
                        .estado(true)
                        .build()
                )
        );
    }

    @Transactional
    public List<DireccionTelefonoEntity> crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, TerceroEntity tercero) {
        TerceroDireccionEntity direccion = crearDireccionUnionTercero(modelMapper.map(direccionTelefonos, DireccionReqDTO.class), tercero);
        return direccionTelefonos.getTelefonos().stream().map(
                telefono -> telefonoAdapter.unionTelefonoDireccion(
                        direccion,
                        telefonoAdapter.crearTelefonoUnionTercero(telefono, tercero),
                        false
                )
        ).toList();
    }
}
