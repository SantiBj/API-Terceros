package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.role.RolRepository;
import com.sigloV1.dao.repositories.role.RolTipoTerceroRep;
import com.sigloV1.dao.repositories.role.TerceroRolRep;
import com.sigloV1.dao.repositories.role.TipoRolRepository;
import com.sigloV1.service.adapters.RolAdapter;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.adapters.TipoTerceroAdapter;
import com.sigloV1.service.interfaces.ITerceroRol;
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
import java.util.Objects;


@Service
public class RolService implements ITerceroRol,IRolesService, ITipoRolService, RolAdapter {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TerceroRolRep terceroRolRep;

    @Autowired
    private TerceroAdapter terceroAdapter;

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

    //TERCERO_ROL_TIPO_TERCERO

    public <T> TerceroRolTipoTerEntity relacionarTerceroRol(T tercero,Long fullRol ){
        TerceroEntity terceroEntity = tercero instanceof TerceroEntity ?
                (TerceroEntity) tercero :   terceroAdapter.obtenerTerceroOException((Long)tercero);
        RolTipoTerceroEntity rol = obtenerRolTipoTerceroOException(fullRol);

        if(terceroEntity.getTipoTercero().getId() != rol.getTipoTercero().getId()){
            throw new BadRequestCustom("No es posible asignar el rol al tercero debido a que el tipo de persona" +
                    " asignada al rol no coincide con el tipo de persona del tercero.");
        }

        if(terceroRolRep.findByTerceroAndRol(terceroEntity,rol).isPresent()){
            throw new BadRequestCustom("El tercero ya cuenta con este rol.");
        }
        return terceroRolRep.save(
                TerceroRolTipoTerEntity
                        .builder()
                        .tercero(terceroEntity)
                        .rol(rol)
                        .estado(true)
                        .build()
        );
    }

    public void estadoTerceroRol(Long relacionTerceroRolId,Boolean estado){
        TerceroRolTipoTerEntity relacion = terceroRolRep.findById(relacionTerceroRolId)
                .orElseThrow(()->new BadRequestCustom("La relacion no existe"));
        relacion.setEstado(estado);
        terceroRolRep.save(relacion);
    }



    //ROLES_TIPO_TERCERO

    public RolTipoTerceroEntity obtenerRolTipoTerceroOException(Long relacionId){
        return rolTipoTerceroRep.findById(relacionId)
                .orElseThrow(()->new BadRequestCustom("El rol no existe."));
    }

    @Override
    public List<RolRelacionResDTO> rolesPorTipoTercero(Long tipoTerceroId) {
        return rolTipoTerceroRep.findByTipoTercero(tipoTerceroAdapter.obtenerTerceroOException(tipoTerceroId))
                .stream().map((rol)->
                        RolRelacionResDTO.builder()
                                .idRol(rol.getRol().getId())
                                .nombre(rol.getRol().getNombre())
                                .tipoRol(rol.getRol().getTipoRol().getId())
                                .idRelacionTipoTercero(rol.getId())
                                .build()
                        ).toList();
    }

    @Transactional
    @Override
    public void crearRolAsociado(RolAsociacionesReqDTO rolData) {
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

    @Override
    public void addTipoTercero(Long tipoTerceroId, Long rolId) {
        //PARA ROLES YA EXISTENTES
        TipoTerceroEntity tipo = tipoTerceroAdapter.obtenerTerceroOException(tipoTerceroId);
        RolEntity rol = logicRol.obtenerRolOException(rolId);
        rolTipoTerceroRep.findByTipoTerceroAndRol(tipo,rol)
                .orElseGet(()->rolTipoTerceroRep.save(RolTipoTerceroEntity.builder()
                        .tipoTercero(tipo)
                        .rol(rol)
                        .build()));
    }

    //ROL
    @Override
    public RolEntity editarRol(RolReqDTO rolData) {
        RolEntity rol = logicRol.obtenerRolOException(rolData.getId());
        TipoRolEntity tipoRol = logicRol.obtenerTipoRolOException(rolData.getTipoRol());
        if(rolRepository.findByNombreIgnoreCaseAndTipoRol(rolData.getNombre(),tipoRol).isPresent()){
            throw new BadRequestCustom("Los datos que envio ya existen.");
        }

        rol.setNombre(rolData.getNombre() != null ? rolData.getNombre() : rol.getNombre());
        rol.setTipoRol(rolData.getTipoRol() != null ? tipoRol : rol.getTipoRol());

        return rolRepository.save(rol);
    }

    //TIPO_ROL

    @Override
    public List<TipoRolResDTO> allTipoRoles() {
        return tipoRolRepository.findAll().stream().map(tipoRol->
                    modelMapper.map(tipoRol,TipoRolResDTO.class)
                ).toList();
    }

    @Override
    public void guardarTipoRol(TipoRolReqDTO tipoRolData) {
        if(tipoRolRepository.findByNombre(tipoRolData.getNombre()).isPresent()) {
            throw new BadRequestCustom("El tipo de rol ya existe.");
        }

        tipoRolRepository.save(
                TipoRolEntity.builder()
                        .nombre(tipoRolData.getNombre())
                        .build()
        );
    }

    @Override
    public TipoRolResDTO editarNombreTipoRol(TipoRolReqDTO tipoRolNuevo, Long tipoRolId) {
        TipoRolEntity tipoRol = logicRol.obtenerTipoRolOException(tipoRolId);

        tipoRolRepository.findByNombre(tipoRolNuevo.getNombre()).ifPresent(value->{
            throw new BadRequestCustom("El nombre que desea asignar al tipo de rol ya esta siendo usado.");
        });

        tipoRol.setNombre(tipoRolNuevo.getNombre());
        return modelMapper.map(tipoRolRepository.save(tipoRol),TipoRolResDTO.class);
    }
}
