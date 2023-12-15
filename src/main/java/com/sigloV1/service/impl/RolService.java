package com.sigloV1.service.impl;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TipoRolEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.dao.repositories.role.RolRepository;
import com.sigloV1.dao.repositories.role.RolTipoTerceroRep;
import com.sigloV1.dao.repositories.role.TipoRolRepository;
import com.sigloV1.service.adapters.TipoTerceroAdapter;
import com.sigloV1.service.interfaces.rol.IRolesService;
import com.sigloV1.service.interfaces.rol.ITipoRolService;

import com.sigloV1.web.dtos.req.rol.RolAsociacionesReqDTO;
import com.sigloV1.web.dtos.req.rol.RolReqDTO;
import com.sigloV1.web.dtos.req.rol.TipoRolReqDTO;
import com.sigloV1.web.dtos.res.rol.RolRelacionResDTO;
import com.sigloV1.web.dtos.res.rol.RolResDTO;
import com.sigloV1.web.dtos.res.rol.TipoRolResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sigloV1.service.logica.LogicRol;
import java.util.List;


@Service
public class RolService implements IRolesService, ITipoRolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolTipoTerceroRep rolTipoTerceroRep;

    @Autowired
    private LogicRol logicRol;

    @Autowired
    private TipoTerceroAdapter tipoTerceroAdapter;

    @Autowired
    private TipoRolRepository tipoRolRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<RolRelacionResDTO> rolesPorTipoTercero(Long tipoTerceroId) {
        return rolTipoTerceroRep.findByTipoTercero(tipoTerceroAdapter.obtenerTerceroOException(tipoTerceroId))
                .stream().map((rol)->
                        RolRelacionResDTO.builder()
                                .idRol(rol.getRol().getId())
                                .nombre(rol.getRol().getNombre())
                                .tipoRol(rol.getRol().getTipoRol().getId())
                                .relacionTipoTercero(rol.getId())
                                .tipoTerceroRelacionado(rol.getTipoTercero().getId())
                                .build()
                        ).toList();
    }

    @Transactional
    @Override
    public void guardarRolAsociado(RolAsociacionesReqDTO rolData) {
        List<TipoTerceroEntity> tipoTercero = rolData.getTipoTercerosId()
                .stream().map(tipoId->
                        tipoTerceroAdapter.obtenerTerceroOException(tipoId)
                        ).toList();

        RolEntity rol = rolRepository.findByNombreIgnoreCase(rolData.getNombre())
                .orElseGet(()-> rolRepository.save(
                        RolEntity.builder()
                                .nombre(rolData.getNombre())
                                .tipoRol(logicRol.obtenerTipoRolOException(rolData.getTipoRol()))
                                .build()
                ));

        logicRol.relacionRolAndTipoTercero(rol,tipoTercero);
    }

    @Transactional
    @Override
    public void eliminarRolAsociaciones(Long rolId) {
        RolEntity rol = logicRol.obtenerRolOException(rolId);
        rolTipoTerceroRep.deleteAll(
                rolTipoTerceroRep.findByRol(rol)
        );
        rolRepository.delete(rol);
    }

    @Override
    public void eliminarAsociacionTipoTercero(Long asociacionId) {
        rolTipoTerceroRep.delete(
                rolTipoTerceroRep.findById(asociacionId)
                        .orElseThrow(()->new BadRequestCustom("La asociacion no existe."))
        );
    }

    @Override
    public RolResDTO editarRol(RolReqDTO rolData) {
        RolEntity rol = logicRol.obtenerRolOException(rolData.getId());

        if (!rol.getNombre().equalsIgnoreCase(rolData.getNombre())){
            rolRepository.findByNombreIgnoreCase(rolData.getNombre()).ifPresent(
                    value -> {
                        throw new BadRequestCustom("El nombre que desa asignar a la rol ya esta siendo usado por otro rol");
                    }
            );
        }

        rol.setNombre(rolData.getNombre());
        rol.setTipoRol(rolData.getTipoRol() != null ?
                logicRol.obtenerTipoRolOException(rolData.getTipoRol()) : rol.getTipoRol()
                );

        RolEntity rolUpdated = rolRepository.save(rol);

        return RolResDTO.builder()
                .id(rolUpdated.getId())
                .nombre(rolUpdated.getNombre())
                .tipoRol(rolUpdated.getTipoRol().getId())
                .build();
    }


    //PARA ROLES YA EXISTENTES
    @Override
    public void addTipoTercero(Long tipoTerceroId, Long rolId) {
        TipoTerceroEntity tipo = tipoTerceroAdapter.obtenerTerceroOException(tipoTerceroId);
        RolEntity rol = logicRol.obtenerRolOException(rolId);
        rolTipoTerceroRep.findByTipoTerceroAndRol(tipo,rol)
                .orElseGet(()->rolTipoTerceroRep.save(RolTipoTerceroEntity.builder()
                        .tipoTercero(tipo)
                        .rol(rol)
                        .build()));
    }


    //tipo rol


    @Override
    public List<TipoRolResDTO> allTipoRoles() {
        return tipoRolRepository.findAll().stream().map(tipoRol->
                    modelMapper.map(tipoRol,TipoRolResDTO.class)
                ).toList();
    }

    @Override
    public void guardarTipoRol(TipoRolReqDTO tipoRolData) {
        tipoRolRepository.findByNombre(tipoRolData.getNombre())
                .orElseGet(()->{
                    return tipoRolRepository.save(
                            TipoRolEntity.builder()
                                    .nombre(tipoRolData.getNombre())
                                    .build()
                    );
                });
    }

    @Override
    public TipoRolResDTO editarNombreTipoRol(TipoRolReqDTO tipoRolNuevo, Long tipoRolId) {
        TipoRolEntity tipoRol = logicRol.obtenerTipoRolOException(tipoRolId);

        if(!tipoRol.getNombre().equalsIgnoreCase(tipoRolNuevo.getNombre())){
            tipoRolRepository.findByNombre(tipoRolNuevo.getNombre()).ifPresent(value->{
                throw new BadRequestCustom("El nombre que desea asignar al tipo de rol ya esta siendo usado.");
            });
        }
        tipoRol.setNombre(tipoRolNuevo.getNombre());
        return modelMapper.map(tipoRolRepository.save(tipoRol),TipoRolResDTO.class);
    }

    @Transactional
    @Override
    public void eliminarTipoRol(Long tipoRolId) {
        TipoRolEntity tipoRol = logicRol.obtenerTipoRolOException(tipoRolId);
        rolRepository.findByTipoRol(tipoRol).forEach(rol->{
            eliminarRolAsociaciones(rol.getId());
        });
        tipoRolRepository.delete(tipoRol);
    }
}
