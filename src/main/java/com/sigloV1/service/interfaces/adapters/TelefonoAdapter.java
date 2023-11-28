package com.sigloV1.service.interfaces.adapters;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;

import java.util.List;

public interface TelefonoAdapter {
    List<TelefonoEntity> crearTelefonosEntity(List<TelefonoReqDTO> telefonos);
    void addTelefonoTercero(List<TelefonoEntity> telefono, TerceroEntity tercero);

    void addTelefonosDireccion(Long direccion, List<TelefonoEntity> telefonos);
}
