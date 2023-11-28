package com.sigloV1.service.impl;


import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.TerceroTelefonoRepository;
import com.sigloV1.service.interfaces.adapters.DireccionAdapter;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService implements TelefonoAdapter {

    @Autowired
    TelefonoRepository telefonoRepository;

    @Autowired
    TerceroTelefonoRepository terceroTelefonoRepository;

    @Autowired
    DireccionAdapter direccionAdapter;

    public List<TelefonoEntity> crearTelefonosEntity(List<TelefonoReqDTO> telefonos){
        return telefonos.stream().map(t->{
            Optional<TelefonoEntity> telefonoExisted = telefonoRepository.findByNumero(t.getNumero());
            if (telefonoExisted.isPresent()){
                return telefonoExisted.get();
            }else{
                TelefonoEntity newTelefono = TelefonoEntity.builder()
                        .numero(t.getNumero())
                        .tipoTelefono(t.getTipoTelefono())
                        .extension(t.getExtension())
                        .build();
                newTelefono = telefonoRepository.save(newTelefono);
                return newTelefono;
            }
        }).toList();
    }

    public void addTelefonoTercero(List<TelefonoEntity> telefono, TerceroEntity tercero){
        List<TerceroTelefonoEntity> relaciones = telefono.stream().map(
                t-> TerceroTelefonoEntity.builder()
                        .tercero(tercero)
                        .telefono(t)
                        .build()
        ).toList();
        terceroTelefonoRepository.saveAll(relaciones);
    }

    public void addTelefonosDireccion(Long direccion, List<TelefonoEntity> telefonos) {
        DireccionEntity direccionEntity = direccionAdapter.obtenerDireccionOException(direccion);
        List<DireccionTelefonoEntity> relaciones = telefonos.stream().map(telefono -> (
                DireccionTelefonoEntity.builder()
                        .telefono(telefono)
                        .direccion(direccionEntity)
                        .build()
        )).toList();
        direccionAdapter.saveTelefonosInDireccion(relaciones);
    }

}
