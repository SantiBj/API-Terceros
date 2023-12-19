package com.sigloV1.service.interfaces.direccionesTelefonos;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomTelefono;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;

public interface ITelefonoService {

    ReturnCustomTelefono crearTelefono(TelefonoReqDTO dataTelefono);
}
