package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CiudadEntity;
import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.repositories.CiudadRepository;
import com.sigloV1.service.interfaces.ICiudadService;
import com.sigloV1.service.adapters.CiudadAdapter;
import com.sigloV1.service.adapters.EstadoAdapter;
import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReq;
import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReqDir;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTORes;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTOResDir;
import com.sigloV1.web.dtos.res.ciudad.DireccionResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadService implements ICiudadService, CiudadAdapter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private EstadoAdapter estadoAdapter;

    public CiudadEntity obtenerCiudadOException(Long id) {
        CiudadEntity ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("La ciudad no existe"));
        if (!ciudad.getEstado().getPais().getEstado()) {
            throw new BadRequestCustom("El pais al que pertenece la ciudad se encuentra desactivado");
        } else {
            return ciudad;
        }
    }

    @Override
    public CiudadDTORes obtenerCiudad(Long id) {
        CiudadEntity ciudadEntity = obtenerCiudadOException(id);
        return CiudadDTORes.builder()
                .id(ciudadEntity.getId())
                .nombre(ciudadEntity.getNombre())
                .indicativo(ciudadEntity.getIndicativo())
                .estado(ciudadEntity.getEstado().getId())
                .build();
    }

    @Override
    public CiudadDTORes crearCiudad(CiudadDTOReq ciudad) {
        CiudadEntity newCiudad = CiudadEntity.builder()
                .nombre(ciudad.getNombre())
                .indicativo(ciudad.getIndicativo())
                .estado(estadoAdapter.obtenerEstadoOException(ciudad.getEstado()))
                .build();
        newCiudad = ciudadRepository.save(newCiudad);
        return CiudadDTORes.builder()
                .id(newCiudad.getId())
                .nombre(newCiudad.getNombre())
                .indicativo(newCiudad.getIndicativo())
                .estado(newCiudad.getEstado().getId())
                .build();
    }

    @Override
    public CiudadDTOResDir crearCiudadConDirecciones(CiudadDTOReqDir ciudad) {
        CiudadEntity newCiudad = CiudadEntity.builder()
                .nombre(ciudad.getNombre())
                .indicativo(ciudad.getIndicativo())
                .estado(estadoAdapter.obtenerEstadoOException(ciudad.getEstado()))
                .build();
        CiudadEntity finalNewCiudad = newCiudad;
        newCiudad.setDirecciones(ciudad.getDirecciones().stream().map(direccionDTO -> (
                DireccionEntity.builder()
                        .nombre(direccionDTO.getNombre())
                        .direccion(direccionDTO.getDireccion())
                        .codigoPostal(direccionDTO.getCodigoPostal())
                        .ciudad(finalNewCiudad)
                        .build()
        )).toList());
        newCiudad = ciudadRepository.save(newCiudad);
        return CiudadDTOResDir.builder()
                .id(newCiudad.getId())
                .nombre(newCiudad.getNombre())
                .indicativo(newCiudad.getIndicativo())
                .estado(newCiudad.getEstado().getId())
                .direcciones(newCiudad.getDirecciones().stream().map(direccion->modelMapper.map(direccion, DireccionResDTO.class)).toList())
                .build();
    }

    @Override
    public void eliminarCiudad(Long id) {
        CiudadEntity ciudad = obtenerCiudadOException(id);
        if (!ciudad.getEstado().getPais().getEstado()){
            throw new BadRequestCustom("El pais asociado a "+ ciudad.getNombre() +" se encuentra desactivado activelo para poder eliminar la ciudad.");
        }else{
            ciudadRepository.delete(ciudad);
        }
    }

    @Override
    public CiudadDTORes editarCiudad(CiudadDTOReq newCiudad, Long id) {
        CiudadEntity ciudad = obtenerCiudadOException(id);
        if (!ciudad.getEstado().getPais().getEstado()){
            throw new BadRequestCustom("El pais asociado a "+ ciudad.getNombre() +" se encuentra desactivado activelo para poder editar la ciudad.");
        }
        ciudad.setNombre(newCiudad.getNombre());
        ciudad.setIndicativo(newCiudad.getIndicativo());
        ciudad.setEstado(estadoAdapter.obtenerEstadoOException(newCiudad.getEstado()));
        ciudad = ciudadRepository.save(ciudad);
        return CiudadDTORes.builder()
                .id(ciudad.getId())
                .nombre(ciudad.getNombre())
                .indicativo(ciudad.getIndicativo())
                .estado(ciudad.getEstado().getId())
                .build();
    }
}
