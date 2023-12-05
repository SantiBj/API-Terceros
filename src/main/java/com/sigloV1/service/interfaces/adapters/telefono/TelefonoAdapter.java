package com.sigloV1.service.interfaces.adapters.telefono;

import com.sigloV1.dao.models.*;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;

public interface TelefonoAdapter {
    DireccionTelefonoEntity unionTelefonoDireccion(TerceroDireccionEntity direccion, TerceroTelefonoEntity telefono,Boolean contacto);

    <T> TerceroTelefonoEntity crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero);

    void estadoTelefonosTerceroDeDireccion(TerceroDireccionEntity direccion,Boolean estado);

    void eliminarRelacionDireccionYTercero(TerceroDireccionEntity direccion);

}