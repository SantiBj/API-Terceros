package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.DireccionRepository;
import com.sigloV1.service.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.direccionesTelefonos.IDireccionService;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService implements IDireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CiudadAdapter ciudadAdapter;

    @Override
    public DireccionEntity crearDireccion(DireccionReqDTO dataDireccion) {
        return direccionRepository.findByDireccionIgnoreCase(dataDireccion.getDireccion())
                .orElseGet(()->direccionRepository.save(
                        DireccionEntity.builder()
                                .nombre(dataDireccion.getNombre())
                                .direccion(dataDireccion.getDireccion())
                                .codigoPostal(dataDireccion.getCodigoPostal())
                                .ciudad(ciudadAdapter.obtenerCiudadOException(dataDireccion.getCiudad()))
                                .build()
                ));
    }
}
