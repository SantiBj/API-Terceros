package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;

import java.util.List;

public interface IEmailService {
    <T> EmailResDTO crearEmailTercero(EmailReqDTO datos, T terceroRol);
    <T> EmailResDTO crearEmailContacto(EmailReqDTO datos,T contacto);

    List<EmailResDTO> obtenerEmailTerceroRol(Long idTerceroRol);

    List<EmailResDTO> obtenerEmailContacto(Long idContacto);

    void estadoEmailTercero(Long idRelacion,Boolean estado);

    void eliminarRelacionEmail(Long idRelacion);
}
