package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.pais.PaisDTOReq;
import com.sigloV1.web.dtos.req.pais.PaisDTOReqBi;
import com.sigloV1.web.dtos.res.pais.PaisDTORes;
import com.sigloV1.web.dtos.res.pais.PaisDTOResBi;

import java.util.List;

public interface IPaisService {
    List<PaisDTORes> obtenerPaises();
    PaisDTOResBi obtenerPais(Long id);

    PaisDTOResBi crearPaisBi(PaisDTOReqBi pais);

    PaisDTORes crearPais(PaisDTOReq pais);

    PaisDTORes desactivarPais(Long id);

    List<PaisDTORes> paisesDesactivados();

   PaisDTOResBi editarPais(PaisDTOReq pais,Long id);

}
