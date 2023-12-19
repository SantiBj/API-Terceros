package com.sigloV1.service.interfaces.direccionesTelefonos;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomDireccion;
import com.sigloV1.web.dtos.req.DirTelTerCon.DireccionReqDTO;

public interface IDireccionService {
    ReturnCustomDireccion crearDireccion(DireccionReqDTO dataDireccion);

    DireccionEntity obtenerDireccionOException(Long direccionId);

}
