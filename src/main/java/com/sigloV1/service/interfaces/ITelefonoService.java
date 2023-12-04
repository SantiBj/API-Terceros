package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.TerceroTelefonoEntity;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;

public interface ITelefonoService {

    <T> TerceroTelefonoEntity crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero);

    void desactivarTelefonoTercero(Long idTelefono,Long idTercero);
}
