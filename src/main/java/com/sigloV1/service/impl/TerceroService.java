package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.role.RolTipoTerceroRep;
import com.sigloV1.service.adapters.*;
import com.sigloV1.service.interfaces.ITerceroService;
import com.sigloV1.service.logica.tercero.Tercero;
import com.sigloV1.web.dtos.req.contacto.ContactoReqEntity;
import com.sigloV1.web.dtos.req.tercero.TerceroContactoDTO;
import com.sigloV1.web.dtos.req.tercero.TerceroReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TerceroService implements ITerceroService {

    @Autowired
    private Tercero terceroLogic;

    @Autowired
    private RolAdapter rolAdapter;

    @Autowired
    private RolTipoTerceroRep rolTipoTerceroRep;

    @Autowired
    private ContactoAdapter contactoAdapter;

    @Autowired
    private TerceroAdapter validacionExistencia;


    @Transactional
    public Long crearTercero(TerceroReqDTO data){
        TerceroEntity newTercero = terceroLogic.crearTercero(data,data.getDocDetalles());

        List<TerceroRolTipoTerEntity> terceroRoles = data.getRoles().stream().map(r->{
            return rolAdapter.relacionarTerceroRol(newTercero,r);
        }).toList();

        terceroLogic.crearDirecciones(data.getDirecciones(),newTercero,null);
        terceroLogic.crearTelefonos(data.getTelefonos(),newTercero,null);
        terceroLogic.crearEmails(data.getEmails(),terceroRoles,null);

        return newTercero.getId();
    }

    @Transactional
    public Long crearContacto(TerceroContactoDTO data){
        TerceroEntity terceroContacto = terceroLogic.crearTercero(data, data.getDocDetalles());
        TerceroEntity tercero = validacionExistencia.obtenerTerceroOException(data.getContactoDe());


        //creacion contacto
        ContactoEntity contacto = contactoAdapter.crearContacto(ContactoReqEntity
                .builder()
                        .tercero(tercero)
                        .contacto(terceroContacto)
                        .cargo(data.getCargoId())
                .build());


        //creacion direcciones y telefonos
        terceroLogic.crearDirecciones(data.getDirecciones(),terceroContacto,contacto);
        terceroLogic.crearTelefonos(data.getTelefonos(),terceroContacto,contacto);
        terceroLogic.crearEmails(data.getEmails(),null,contacto);

        return contacto.getId();
    }


}
