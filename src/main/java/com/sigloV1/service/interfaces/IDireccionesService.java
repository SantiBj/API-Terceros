package com.sigloV1.service.interfaces;


import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTOTel;

import java.util.List;

public interface IDireccionesService {

    DireccionResDTO obtenerDireccion(Long id);

    DireccionResDTOTel obtenerDireccionConTelefonos(Long id);

    DireccionResDTO crearDireccion(DireccionReqDTO direccion);

    DireccionResDTO editarDireccion(DireccionReqDTO direccion,Long id);

    void eliminarDireccion(Long id);
}
