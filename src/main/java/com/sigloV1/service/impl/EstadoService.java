package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CiudadEntity;
import com.sigloV1.dao.models.EstadoEntity;
import com.sigloV1.dao.repositories.EstadoRepository;
import com.sigloV1.service.interfaces.IEstadoService;
import com.sigloV1.service.adapters.EstadoAdapter;
import com.sigloV1.service.adapters.PaisAdapter;
import com.sigloV1.web.dtos.req.estado.EstadoDTOReqBiPais;
import com.sigloV1.web.dtos.req.estado.EstadoDTOReqPais;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResBiPais;
import com.sigloV1.web.dtos.res.pais.CiudadDTORes;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResPais;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EstadoService implements IEstadoService, EstadoAdapter {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaisAdapter paisAdapter;

    //helper
    public EstadoEntity obtenerEstadoOException(Long id) {
        EstadoEntity estado = estadoRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("El estado no existe"));
        if (!estado.getPais().getEstado()) {
            throw new BadRequestCustom("El pais al que pertenece el estado se encuentra desactivado.");
        } else {
            return estado;
        }
    }

    private <Inp, Out> List<Out> transformList(List<Inp> ciudades, Class<Out> ClassOutput) {
        return ciudades.stream().map(
                ciudad -> modelMapper.map(ciudad, ClassOutput)
        ).toList();
    }

    @Override
    public EstadoDTOResBiPais obtenerEstado(Long id) {
        EstadoEntity estadoEntity = obtenerEstadoOException(id);
        return EstadoDTOResBiPais.builder()
                .id(estadoEntity.getId())
                .nombre(estadoEntity.getNombre())
                .ciudades(transformList(estadoEntity.getCiudades(), CiudadDTORes.class))
                .pais(estadoEntity.getPais().getId())
                .build();
    }

    @Override
    public EstadoDTOResBiPais crearEstadoBi(EstadoDTOReqBiPais estado) {
        //podra crear las ciudades
        EstadoEntity estadoEntity = EstadoEntity.builder()
                .nombre(estado.getNombre())
                .pais(paisAdapter.obtenerPaisOException(estado.getPais()))
                .ciudades(transformList(estado.getCiudades(), CiudadEntity.class))
                .build();
        if (!estadoEntity.getPais().getEstado()){
            throw new BadRequestCustom("El pais "+ estadoEntity.getPais().getNombrePais() + " se encuentra desactivado");
        }
        EstadoEntity finalEstadoEntity = estadoEntity;
        estadoEntity.getCiudades().forEach(ciudad -> ciudad.setEstado(finalEstadoEntity));
        estadoEntity = estadoRepository.save(estadoEntity);
        return EstadoDTOResBiPais.builder()
                .id(estadoEntity.getId())
                .nombre(estadoEntity.getNombre())
                .ciudades(transformList(estadoEntity.getCiudades(), CiudadDTORes.class))
                .pais(estadoEntity.getPais().getId())
                .build();
    }

    @Override
    public EstadoDTOResPais crearEstado(EstadoDTOReqPais estado) {
        EstadoEntity estadoEntity = EstadoEntity.builder()
                .nombre(estado.getNombre())
                .pais(paisAdapter.obtenerPaisOException(estado.getPais()))
                .build();
        if (!estadoEntity.getPais().getEstado()){
            throw new BadRequestCustom("El pais "+ estadoEntity.getPais().getNombrePais() + " se encuentra desactivado");
        }
        estadoEntity.setPais(paisAdapter.obtenerPaisOException(estado.getPais()));
        estadoEntity = estadoRepository.save(estadoEntity);
        return EstadoDTOResPais.builder()
                .id(estadoEntity.getId())
                .nombre(estadoEntity.getNombre())
                .pais(estadoEntity.getPais().getId())
                .build();
    }

    @Override
    public void eliminarEstado(Long id) {
        obtenerEstadoOException(id);
        estadoRepository.deleteById(id);
    }

    @Override
    public EstadoDTOResPais editarEstado(EstadoDTOReqPais estado, Long id) {
        EstadoEntity estadoEntity = obtenerEstadoOException(id);
        if (!Objects.equals(estado.getPais(), estadoEntity.getPais().getId())) {
            estadoEntity.setPais(paisAdapter.obtenerPaisOException(estado.getPais()));
        }
        estadoEntity.setNombre(estado.getNombre());
        estadoEntity = estadoRepository.save(estadoEntity);
        return EstadoDTOResPais.builder()
                .id(estadoEntity.getId())
                .nombre(estadoEntity.getNombre())
                .pais(estadoEntity.getPais().getId())
                .build();
    }
}
