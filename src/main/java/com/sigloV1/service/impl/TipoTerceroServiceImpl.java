package com.sigloV1.service.impl;


import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.dao.repositories.tercero.TipoTeceroRepository;
import com.sigloV1.service.adapters.TipoTerceroAdapter;
import com.sigloV1.service.interfaces.ITipoTerceroService;
import com.sigloV1.web.dtos.req.TipoTerceroDTORq;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTerceroServiceImpl implements ITipoTerceroService, TipoTerceroAdapter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TipoTeceroRepository tipoTeceroRepository;


    public TipoTerceroEntity obtenerTerceroOException(Long id) {
        return tipoTeceroRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("El tipo de tercero "+id+" no existe."));
    }

    @Override
    public List<TipoTerceroEntity> listaTipoTerceros() {
        return tipoTeceroRepository.findAll();
    }

    public void estadoTipoTercero(Long tipoTerceroId,Boolean estado){
        TipoTerceroEntity tipo = obtenerTerceroOException(tipoTerceroId);
        tipo.setEstado(estado);
        tipoTeceroRepository.save(tipo);
    }

    @Override
    public void crearTipoTercero(TipoTerceroDTORq tipoTercero) {
        if(tipoTeceroRepository.findByNombreIgnoreCase(tipoTercero.getNombre()).isPresent()){
            throw new BadRequestCustom("El tipo de tercero ya existe.");
        }
        tipoTeceroRepository.save(
                TipoTerceroEntity
                        .builder()
                        .nombre(tipoTercero.getNombre())
                        .estado(true)
                        .build()
        );
    }


    @Override
    public TipoTerceroEntity editarTipoTercero(TipoTerceroDTORq tipoTercero, Long id) {
        TipoTerceroEntity tipo = obtenerTerceroOException(id);
        if(tipoTeceroRepository.findByNombreIgnoreCase(tipoTercero.getNombre()).isPresent()){
            throw new BadRequestCustom("El nombre ya esta en uso");
        }
        tipo.setNombre(tipoTercero.getNombre());
        return tipoTeceroRepository.save(tipo);
    }
}
