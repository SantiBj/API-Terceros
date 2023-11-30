package com.sigloV1.service.impl;

import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.TerceroRepository;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerceroService implements TerceroAdapter {

    @Autowired
    private TerceroRepository terceroRepository;

    @Override
    public TerceroEntity obtenerTerceroOException(Long id) {
        return terceroRepository.findById(id)
                .orElseThrow(()->new BadRequestCustom("El tercero no existe"));
    }
}