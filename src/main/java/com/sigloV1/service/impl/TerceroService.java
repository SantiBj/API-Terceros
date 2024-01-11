package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DocDetallesRep;
import com.sigloV1.dao.repositories.tercero.TerceroRepository;
import com.sigloV1.dao.repositories.tercero.TerceroRolTipoTerRepository;
import com.sigloV1.service.adapters.*;
import com.sigloV1.service.logica.Tercero;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import com.sigloV1.web.dtos.req.tercero.TerceroReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TerceroService{

    @Autowired
    private TerceroRepository terceroRepository;

    @Autowired
    private Tercero terceroLogic;

    @Autowired
    private RolAdapter rolAdapter;

    @Autowired
    private DatosContactoAdapter datosContactoAdapter;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Long crearTercero(TerceroReqDTO data){
        TerceroEntity newTercero = terceroLogic.crearTercero(data);

        List<TerceroRolTipoTerEntity> terceroRoles = data.getRoles().stream().map(r->{
            return rolAdapter.relacionarTerceroRol(newTercero,r);
        }).toList();

        data.getDirecciones().forEach(d->{
            if(d.getTelefonos().size() > 0){
                datosContactoAdapter.crearTelefonosAsociarNuevaDireccion(
                        modelMapper.map(d,DireccionTelefonosReqDTO.class),
                        newTercero
                );
            }else{
                datosContactoAdapter.crearDireccionAsociarTercero(
                        modelMapper.map(d, DireccionReqDTO.class)
                        ,newTercero);
            }
        });

        data.getTelefonos().forEach(t->{
            datosContactoAdapter.crearTelefonoAsociarTercero(
                    modelMapper.map(t, TelefonoReqDTO.class)
                    ,newTercero);
        });

        //id -> rol_tipo_tercero
        data.getEmails().forEach(e->{
            RolTipoTerceroEntity rol = rolAdapter.obtenerRolTipoTerceroOException(e.getRol());
            //busco el Tercero_rol de este rol
            TerceroRolTipoTerEntity terceroRol = terceroRoles.stream()
                    .filter(tr->(tr.getRol().getId().equals(rol.getId())))
                    .findFirst()
                    .orElse(null);

            if (terceroRol == null) throw new BadRequestCustom("El rol no esta relacionado con el tercero");

            //crea el correo y lo une al tercero_rol_email
            

        });

    }

    public Long crearContacto(){

    }

}
