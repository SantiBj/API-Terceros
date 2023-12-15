package com.sigloV1.service.logica;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TipoRolEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.dao.repositories.role.RolRepository;
import com.sigloV1.dao.repositories.role.RolTipoTerceroRep;
import com.sigloV1.dao.repositories.role.TipoRolRepository;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogicRol {

    @Autowired
    private TipoRolRepository tipoRolRepository;

    @Autowired
    private RolTipoTerceroRep rolTipoTerceroRep;

    @Autowired
    private RolRepository rolRepository;


    public TipoRolEntity obtenerTipoRolOException(Long tipoRolId) {
        return tipoRolRepository.findById(tipoRolId)
                .orElseThrow(() -> new BadRequestCustom("El tipo de rol "+tipoRolId+" no existe."));
    }

    public RolEntity obtenerRolOException(Long rolId){
        return rolRepository.findById(rolId)
                .orElseThrow(()->new BadRequestCustom("El rol no existe"));
    }

    @Transactional
    public List<RolTipoTerceroEntity> relacionRolAndTipoTercero(RolEntity rol, List<TipoTerceroEntity> tipos) {
        return tipos.stream().map(tipo ->
                rolTipoTerceroRep.findByTipoTerceroAndRol(tipo, rol)
                        .orElseGet(() ->
                                rolTipoTerceroRep.save(
                                        RolTipoTerceroEntity.builder()
                                                .tipoTercero(tipo)
                                                .rol(rol)
                                                .build()
                                )
                        )
        ).toList();
    }

}
