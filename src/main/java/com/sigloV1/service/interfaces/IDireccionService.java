package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.DireccionTelefonoEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionTelefonosResDTO;

import java.util.List;

public interface IDireccionService {

    <D,T> void crearDireccionSegunCorresponda(D direccion,T tercero);

    DireccionResDTO editarDireccion(DireccionReqDTO direccion, Long id);

    void eliminarDireccion(Long idDireccion, Long idTercero);

    void estadoDireccionTercero(Long id, Long idTercero,Boolean estado);

    List<DireccionTelefonosResDTO> obtenerDireccionesPorTercero(Long idTercero);
}
