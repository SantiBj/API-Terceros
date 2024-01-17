package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.tercero.TerceroContactoDTO;
import com.sigloV1.web.dtos.req.tercero.TerceroReqDTO;

public interface ITerceroService {

    Long crearTercero(TerceroReqDTO datos);
    Long crearContacto(TerceroContactoDTO data);

}
