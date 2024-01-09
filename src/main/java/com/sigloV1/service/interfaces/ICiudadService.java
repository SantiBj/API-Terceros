package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReq;
import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReqDir;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTORes;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTOResDir;

public interface ICiudadService {

    CiudadDTORes obtenerCiudad(Long id);

    CiudadDTORes crearCiudad(CiudadDTOReq ciudad);

    void eliminarCiudad(Long id);

    CiudadDTORes editarCiudad(CiudadDTOReq newCiudad, Long id);
}
