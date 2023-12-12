package com.sigloV1.service.impl;

import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.dao.repositories.role.RolRepository;
import com.sigloV1.dao.repositories.role.RolTipoTerceroRep;
import com.sigloV1.service.adapters.TipoTerceroAdapter;
import com.sigloV1.service.interfaces.rol.IRolesService;
import com.sigloV1.web.dtos.req.rol.RolAsociacionesReqDTO;
import com.sigloV1.web.dtos.req.rol.RolReqDTO;
import com.sigloV1.web.dtos.res.rol.RolResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService implements IRolesService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolTipoTerceroRep rolTipoTerceroRep;

    @Autowired
    private TipoTerceroAdapter tipoTerceroAdapter;


    @Override
    public List<RolResDTO> rolesPorTipoTercero(Long tipoTerceroId) {
        return rolTipoTerceroRep.findByTipoTercero(tipoTerceroAdapter.obtenerTerceroOException(tipoTerceroId))
                .stream().map((rol)->
                        RolResDTO.builder()
                                .id(rol.getRol().getId())
                                .nombre(rol.getRol().getNombre())
                                .tipoRol(rol.getRol().getTipoRol().getId())
                                .build()
                        ).toList();
    }

    @Override
    public void guardarRolAsociado(RolAsociacionesReqDTO rolData) {

    }

    @Override
    public void eliminarRolAsociaciones(Long rolId) {

    }

    @Override
    public void eliminarAsociacionTipoTercero(Long asociacionId) {

    }

    @Override
    public RolResDTO editarRol(RolReqDTO rolData) {
        return null;
    }

    @Override
    public void addTipoTercero(Long tipoTerceroId, Long rolId) {

    }
}
