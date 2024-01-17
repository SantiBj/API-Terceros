package com.sigloV1.service.adapters;

import com.sigloV1.dao.models.ContactoEntity;


public interface ContactoAdapter {
    <T> ContactoEntity crearContacto(T dataContacto);
}

