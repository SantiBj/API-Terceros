package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;

public interface IEmailService {
    <T> EmailResDTO crearEmailTercero(EmailReqDTO datos, T tercero);
    <T> EmailResDTO crearEmailContacto(EmailReqDTO datos,T contacto);
}
