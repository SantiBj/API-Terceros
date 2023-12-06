package com.sigloV1.service.impl;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.EmailEntity;
import com.sigloV1.dao.models.TerceroRolEmailContEntity;
import com.sigloV1.dao.models.TerceroRolTipoTer;
import com.sigloV1.dao.repositories.EmailRepository;
import com.sigloV1.dao.repositories.relacionesMaM.RelacionesEmailRepository;
import com.sigloV1.service.interfaces.IEmailService;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.service.logica.email.MetodosEmail;
import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private RelacionesEmailRepository relacionesEmailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MetodosContacto metodosContacto;

    @Autowired
    private MetodosEmail metodosEmail;


    @Override
    public <T> EmailResDTO crearEmailTercero(EmailReqDTO datos, T tercero) {
        TerceroRolTipoTer roleTercero = tercero instanceof TerceroRolTipoTer ?
                (TerceroRolTipoTer) tercero : terceroAdapter.obtenerRolTercero((Long) tercero);

        EmailEntity email = metodosEmail.emailARelacionar(datos);

        TerceroRolEmailContEntity relacion = relacionesEmailRepository.findByTerceroAndEmail(roleTercero, email).orElseGet(() ->
                relacionesEmailRepository.save(
                        TerceroRolEmailContEntity.builder()
                                .email(email)
                                .tercero(roleTercero)
                                .build()
                        )
                );
        return EmailResDTO.builder()
                .id(relacion.getEmail().getId())
                .email(relacion.getEmail().getEmail())
                .estado(relacion.getEstado())
                .build();
    }

    @Override
    public <T> EmailResDTO crearEmailContacto(EmailReqDTO datos, T contacto) {
        ContactoEntity contactoEntity = contacto instanceof ContactoEntity ? (ContactoEntity) contacto
                : metodosContacto.obtenerContactoOException((Long) contacto);

        EmailEntity email = metodosEmail.emailARelacionar(datos);

        TerceroRolEmailContEntity relacion = relacionesEmailRepository
                .findByContactoAndEmail(contactoEntity, email).orElseGet(() ->
                        relacionesEmailRepository.save(
                                TerceroRolEmailContEntity.builder()
                                        .email(email)
                                        .contacto(contactoEntity)
                                        .estado(true)
                                        .build()
                        )
                );
        return EmailResDTO.builder()
                .id(relacion.getEmail().getId())
                .email(relacion.getEmail().getEmail())
                .estado(relacion.getEstado())
                .build();
    }

    public List<EmailResDTO> obtenerEmailTercero(Long idTercero){
        return relacionesEmailRepository.findByTercero(terceroAdapter.obtenerTerceroOException(idTercero))
                .stream().map((email)->{
                    return EmailResDTO.builder()
                            .id(email.getEmail().getId())
                            .email(email.getEmail().getEmail())
                            .estado(email.getEstado())
                            .build();
                }).toList();
    }

    public List<EmailResDTO> obtenerEmailContacto(Long idContacto){
        return relacionesEmailRepository.findByContacto(metodosContacto.obtenerContactoOException(idContacto))
                .stream().map((email)->{
                    return EmailResDTO.builder()
                            .id(email.getEmail().getId())
                            .email(email.getEmail().getEmail())
                            .estado(email.getEstado())
                            .build();
                }).toList();
    }

    public void estadoEmailTercero(Long idTercero,Long idEmail){
        TerceroRolEmailContEntity relacion = relacionesEmailRepository.findByTerceroAndEmail(
                terceroAdapter.obtenerTerceroOException(idTercero),
                metodosEmail.obtenerEmailOException(idEmail)
        );
    }
}
