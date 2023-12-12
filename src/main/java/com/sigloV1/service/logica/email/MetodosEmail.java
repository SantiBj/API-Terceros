package com.sigloV1.service.logica.email;

import com.sigloV1.dao.models.EmailEntity;
import com.sigloV1.dao.repositories.email.EmailRepository;
import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetodosEmail {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmailEntity emailARelacionar(EmailReqDTO datos){
        return emailRepository.findByEmailIgnoreCase(datos.getEmail())
                .orElseGet(() -> emailRepository.save(modelMapper.map(datos, EmailEntity.class)));
    }

    public EmailEntity obtenerEmailOException(Long idEmail){
        return emailRepository.findById(idEmail)
                .orElseThrow(()->new BadRequestCustom("El correo no existe"));
    }
}
