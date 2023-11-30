package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;

public interface IDireccionService {

    <T> void crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos,T tercero);

    <T> TerceroDireccionEntity crearDireccionUnionTercero(DireccionReqDTO direccion, T tercero);

    void desactivarRelacionDireccionTercero(Long direccion, Long tercero);

    DireccionResDTO editarDireccion(DireccionReqDTO direccion, Long id);

    void eliminarDireccion(Long direccion);
}
