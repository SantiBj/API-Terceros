package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroDireccionRepository;
import com.sigloV1.service.interfaces.IDireccionService;
import com.sigloV1.service.interfaces.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService implements IDireccionService {

    @Autowired
    private CiudadAdapter ciudadAdapter;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TerceroDireccionRepository terceroDireccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private TelefonoAdapter telefonoAdapter;


    private DireccionEntity creacionDireccion(DireccionReqDTO direccion) {
        Optional<DireccionEntity> direccionExistente = direccionRepository.findByDireccionIgnoreCase(direccion.getDireccion());
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
        Optional<TerceroDireccionEntity> relacionExistente = terceroDireccionRepository.findByDireccionAndTercero(direccionEntity,tercero);
        return relacionExistente.orElseGet(() -> terceroDireccionRepository.save(TerceroDireccionEntity.builder()
                .tercero(tercero)
                .direccion(direccionEntity)
                .estado(true)
                .build()));
    }


    @Transactional
    public List<DireccionTelefonoEntity> crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, TerceroEntity tercero) {
        TerceroDireccionEntity direccion = crearDireccionUnionTercero(modelMapper.map(direccionTelefonos, DireccionReqDTO.class), tercero);
        return direccionTelefonos.getTelefonos().stream().map(
                telefono -> telefonoAdapter.unionTelefonoDireccion(
                        direccion,
                        telefonoAdapter.crearTelefonoUnionTercero(telefono, tercero)
                )
        ).toList();
    }

    //puede ser llamado desde creacion tercero
    //se puede crear solo con el endpoint direccion-telefonos o solo direccion
    public <D, T> void crearDireccionSegunCorresponda(D direccion, T tercero) {
        try {
            //conviertiendo el tercero a entidad
            TerceroEntity terceroEntity;
            if (tercero instanceof TerceroEntity) {
                terceroEntity = (TerceroEntity) tercero;
            } else if (tercero instanceof Long) {
                terceroEntity = terceroAdapter.obtenerTerceroOException((Long) tercero);
            } else {
                throw new BadRequestCustom("La tercero no cumple con el formato indicado");
            }

            if (direccion instanceof DireccionReqDTO) {
                crearDireccionUnionTercero((DireccionReqDTO) direccion, terceroEntity);
            } else if (direccion instanceof DireccionTelefonosReqDTO) {
                crearDireccionTelefonoUnionTercero((DireccionTelefonosReqDTO) direccion, terceroEntity);
            } else {
                throw new BadRequestCustom("La direccion no cumple con el formato indicado");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestCustom("Ocurrio un error intente mas tarde");
        }

    }

    @Override
    public void desactivarRelacionDireccionTercero(Long direccion, Long tercero) {

    }

    @Override
    public DireccionResDTO editarDireccion(DireccionReqDTO direccion, Long id) {
        return null;
    }

    @Override
    public void eliminarDireccion(Long direccion) {

    }
}
