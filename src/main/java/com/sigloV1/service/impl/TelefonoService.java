package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.DireccionTelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroTelefonoRepository;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoService implements TelefonoAdapter {

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private TerceroTelefonoRepository terceroTelefonoRepository;

    @Autowired
    private DireccionTelefonoRepository direccionTelefonoRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public <T> TerceroTelefonoEntity crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero) {
        Optional<TelefonoEntity> telefonoExistente = telefonoRepository.findByNumero(telefono.getNumero());
        TelefonoEntity telefonoAAsignar;
        TerceroEntity terceroAAsignar;

        if (tercero instanceof TerceroEntity) {
            terceroAAsignar = (TerceroEntity) tercero;
        } else {
            terceroAAsignar = terceroAdapter.obtenerTerceroOException((Long) tercero);
        }

        telefonoAAsignar = telefonoExistente.orElseGet(() -> telefonoRepository.save(TelefonoEntity.builder()
                .numero(telefono.getNumero())
                .tipoTelefono(telefono.getTipoTelefono())
                .extension(telefono.getExtension())
                .build()));

        Optional<TerceroTelefonoEntity> relacion = terceroTelefonoRepository.findByTelefonoAndTercero(telefonoAAsignar, terceroAAsignar);
        return relacion.orElseGet(() ->
                terceroTelefonoRepository.save(
                        TerceroTelefonoEntity.builder()
                                .tercero(terceroAAsignar)
                                .telefono(telefonoAAsignar)
                                .estado(true)
                                .build()
                )
        );
    }

    public DireccionTelefonoEntity unionTelefonoDireccion(TerceroDireccionEntity direccion, TerceroTelefonoEntity telefono) {
        Optional<DireccionTelefonoEntity> relacion = direccionTelefonoRepository.findByDireccionTerAndTelefonoTer(direccion, telefono);
        return relacion.orElseGet(() -> direccionTelefonoRepository.save(
                DireccionTelefonoEntity.builder()
                        .telefonoTer(telefono)
                        .direccionTer(direccion)
                        .estado(true)
                        .build()));
    }
}
