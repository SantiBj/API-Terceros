package com.sigloV1.service.adapters;

import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;

public interface EmailCreacionAdapter {

    <T> EmailResDTO crearEmailTercero(EmailReqDTO datos, T terceroRol);
    <T> EmailResDTO crearEmailContacto(EmailReqDTO datos, T contacto);

}
