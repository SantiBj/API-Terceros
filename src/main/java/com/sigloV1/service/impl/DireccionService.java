package com.sigloV1.service.impl;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroDireccionRepository;
import com.sigloV1.service.interfaces.IDireccionService;
import com.sigloV1.service.interfaces.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private TerceroDireccionEntity asignarDireccionConTercero(DireccionEntity direccion, TerceroEntity tercero) {
        try {
            return terceroDireccionRepository.save(TerceroDireccionEntity.builder()
                    .tercero(tercero)
                    .direccion(direccion)
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestCustom("Error en la petición");
        }
    }

    //eliminar
    @Override
    public <T> void crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, T tercero) {

    }

    @Override
    public <T> TerceroDireccionEntity crearDireccionUnionTercero(DireccionReqDTO direccion, T tercero) {
        if (tercero instanceof TerceroEntity) {
            return asignarDireccionConTercero(creacionDireccion(direccion), (TerceroEntity) tercero);
        } else {
            return asignarDireccionConTercero(creacionDireccion(direccion), terceroAdapter.obtenerTerceroOException((Long) tercero));
        }
    }

    /*
    @Override
    public <T> void crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, T tercero) {
        TerceroDireccionEntity data = crearDireccionUnionTercero(modelMapper.map(direccionTelefonos,DireccionReqDTO.class),tercero);
        direccionTelefonos.getTelefonos().forEach(
                //crear y relacionar las direcciones
        );
    }*/

    public <D,T> void crearDireccionSegunCorresponda(D direccion,T tercero){
        if (direccion instanceof DireccionReqDTO){
            crearDireccionUnionTercero((DireccionReqDTO) direccion,tercero);
        }else{

            //direccion con telefonos

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
