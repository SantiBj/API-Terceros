package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.DireccionRepository;
import com.sigloV1.service.adapters.CiudadAdapter;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomDireccion;
import com.sigloV1.service.interfaces.direccionesTelefonos.IDireccionService;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DireccionService implements IDireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CiudadAdapter ciudadAdapter;

    @Override
    public ReturnCustomDireccion crearDireccion(DireccionReqDTO dataDireccion) {

        Optional<DireccionEntity> direccionExistente = direccionRepository
                .findByDireccionIgnoreCase(dataDireccion.getDireccion());

        if (direccionExistente.isPresent()){
            return ReturnCustomDireccion.builder()
                    .direccion(direccionExistente.get())
                    .alReadyExisted(true)
                    .build();
        }else{
            DireccionEntity nuevaDireccion = direccionRepository.save(
                    DireccionEntity.builder()
                            .direccion(dataDireccion.getDireccion())
                            .codigoPostal(dataDireccion.getCodigoPostal())
                            .ciudad(ciudadAdapter.obtenerCiudadOException(dataDireccion.getCiudad()))
                            .build()
            );

            return ReturnCustomDireccion.builder()
                    .direccion(nuevaDireccion)
                    .alReadyExisted(false)
                    .build();
        }

    }

    @Override
    public DireccionEntity obtenerDireccionOException(Long direccionId) {
        return direccionRepository.findById(direccionId).orElseThrow(()->{
            throw new BadRequestCustom("La direccion no existe.");
        });
    }
}
