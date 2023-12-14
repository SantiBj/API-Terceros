package com.sigloV1.service.logica.contacto;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetodosContacto {

    @Autowired
    ContactoRepository contactoRepository;

    public ContactoEntity obtenerContactoOException(Long id){
        return contactoRepository.findById(id)
                .orElseThrow(()->new BadRequestCustom("El contacto no existe"));
    }

    //creacion de un contacto


}
