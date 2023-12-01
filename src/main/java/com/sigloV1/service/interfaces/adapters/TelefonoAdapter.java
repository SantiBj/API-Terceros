package com.sigloV1.service.interfaces.adapters;

import com.sigloV1.dao.models.*;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;

public interface TelefonoAdapter {
    DireccionTelefonoEntity unionTelefonoDireccion(TerceroDireccionEntity direccion, TerceroTelefonoEntity telefono);

    <T> TerceroTelefonoEntity crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero);
}