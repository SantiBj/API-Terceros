package com.sigloV1.service.impl;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.EmailEntity;
import com.sigloV1.dao.models.TerceroRolEmailContEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;
import com.sigloV1.dao.repositories.email.EmailRepository;
import com.sigloV1.dao.repositories.email.RelacionesEmailRepository;
import com.sigloV1.service.adapters.EmailCreacionAdapter;
import com.sigloV1.service.interfaces.IEmailService;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.service.logica.email.MetodosEmail;
import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService, EmailCreacionAdapter {

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
    public <T> EmailResDTO crearEmailTercero(EmailReqDTO datos, T terceroRol) {
        TerceroRolTipoTerEntity roleTercero = terceroRol instanceof TerceroRolTipoTerEntity ?
                (TerceroRolTipoTerEntity) terceroRol : terceroAdapter.obtenerRolTercero((Long) terceroRol);

        //creacion del email
        EmailEntity email = metodosEmail.emailARelacionar(datos);

        TerceroRolEmailContEntity relacion = relacionesEmailRepository.findByTerceroAndEmail(roleTercero, email).orElseGet(() ->
                relacionesEmailRepository.save(
                        TerceroRolEmailContEntity.builder()
                                .email(email)
                                .tercero(roleTercero)
                                .estado(true)
                                .build()
                        )
                );
        return EmailResDTO.builder()
                .idEmail(relacion.getEmail().getId())
                .idRelacion(relacion.getId())
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
                .idEmail(relacion.getEmail().getId())
                .idRelacion(relacion.getId())
                .email(relacion.getEmail().getEmail())
                .estado(relacion.getEstado())
                .build();
    }

    // El id sera el de la relacion
    public List<EmailResDTO> obtenerEmailTerceroRol(Long idTerceroRol){
        //buscar que la relacion exista en tercero_rol
        return relacionesEmailRepository.findByTercero(terceroAdapter.obtenerRolTercero(idTerceroRol))
                .stream().map((email)->{
                    return EmailResDTO.builder()
                            .idRelacion(email.getId())
                            .idEmail(email.getEmail().getId())
                            .email(email.getEmail().getEmail())
                            .estado(email.getEstado())
                            .build();
                }).toList();
    }

    // El id sera el de la relacion
    public List<EmailResDTO> obtenerEmailContacto(Long idContacto){
        return relacionesEmailRepository.findByContacto(metodosContacto.obtenerContactoOException(idContacto))
                .stream().map((email)->{
                    return EmailResDTO.builder()
                            .idEmail(email.getEmail().getId())
                            .idRelacion(email.getId())
                            .email(email.getEmail().getEmail())
                            .estado(email.getEstado())
                            .build();
                }).toList();
    }

    public void estadoEmailTercero(Long idRelacion,Boolean estado){
        TerceroRolEmailContEntity relacion = relacionesEmailRepository
                .findById(idRelacion).orElseThrow(
                        ()->new BadRequestCustom("La relacion con el correo no existe."));
        relacion.setEstado(estado);
        relacionesEmailRepository.save(relacion);
    }

    public void eliminarRelacionEmail(Long idRelacion){
        TerceroRolEmailContEntity relacion = relacionesEmailRepository.findById(idRelacion)
                .orElseThrow(()->new BadRequestCustom("La relacion con el correo no existe."));
        relacionesEmailRepository.delete(relacion);
    }

}
