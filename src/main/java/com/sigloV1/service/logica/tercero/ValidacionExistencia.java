package com.sigloV1.service.logica.tercero;

import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;
import com.sigloV1.dao.repositories.tercero.TerceroRepository;
import com.sigloV1.dao.repositories.tercero.TerceroRolTipoTerRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionExistencia implements TerceroAdapter {

    @Autowired
    private TerceroRepository terceroRepository;

    @Autowired
    private TerceroRolTipoTerRepository terceroRol;

    @Override
    public TerceroEntity obtenerTerceroOException(Long id) {
        return terceroRepository.findById(id)
                .orElseThrow(()->new BadRequestCustom("El tercero no existe"));
    }


    @Override
    public TerceroRolTipoTerEntity obtenerRolTercero(Long id){
        return terceroRol.findById(id)
                .orElseThrow(()->new BadRequestCustom("El tercero no tiene el rol indicado"));
    }
}
