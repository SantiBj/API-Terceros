package com.sigloV1.service.interfaces.adapters.direccion;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.DireccionTelefonoEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;

import java.util.List;

public interface DireccionAdapter {
    DireccionEntity obtenerDireccionEntityOException(Long id) ;

    TerceroDireccionEntity obtenerRelacionDireccionTercero(Long idDireccion, TerceroEntity tercero);

    List<DireccionTelefonoEntity> crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, TerceroEntity tercero);

    TerceroDireccionEntity crearDireccionUnionTercero(DireccionReqDTO direccion, TerceroEntity tercero);
}
