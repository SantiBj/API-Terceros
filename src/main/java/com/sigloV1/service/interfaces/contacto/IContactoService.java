package com.sigloV1.service.interfaces.contacto;


import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.web.dtos.req.contacto.ContactoReqDTO;
import com.sigloV1.web.dtos.res.contacto.ContactoResDTO;
import com.sigloV1.web.dtos.res.tercero.TerceroResDTO;

import java.util.List;

public interface IContactoService {
    <T> ContactoEntity crearContacto(T dataContacto);

    List<ContactoResDTO> contactosTercero(Long idTercero);

    void eliminarContacto(Long relacionId);

    void estadoContacto(Long relacionId,Boolean estado);
}
