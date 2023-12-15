package com.sigloV1.service.interfaces.direccionesTelefonos;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;

public interface IDireccionService {
    DireccionEntity crearDireccion(DireccionReqDTO dataDireccion);

}
