package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.telefono.TerceroTelefonoResDTO;

public interface ITelefonoService {

    <T> TerceroTelefonoResDTO crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero);
}
