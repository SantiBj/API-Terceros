package com.sigloV1.service.logica.telefono;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.DireccionTelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroTelefonoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.adapters.telefono.TelefonoAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TelefonoLogica implements TelefonoAdapter {

    @Autowired
    private DireccionTelefonoRepository direccionTelefonoRepository;

    @Autowired
    private TerceroTelefonoRepository terceroTelefonoRepository;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private TerceroAdapter terceroAdapter;

    public DireccionTelefonoEntity unionTelefonoDireccion(TerceroDireccionEntity direccion, TerceroTelefonoEntity telefono, Boolean contacto) {
        Optional<DireccionTelefonoEntity> relacion = direccionTelefonoRepository.findByDireccionTerAndTelefonoTer(direccion, telefono);
        return relacion.orElseGet(() -> direccionTelefonoRepository.save(
                DireccionTelefonoEntity.builder()
                        .telefonoTer(telefono)
                        .direccionTer(direccion)
                        .contacto(contacto)
                        .build()));
    }


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

        Optional<TerceroTelefonoEntity> relacion = terceroTelefonoRepository
                .findByTelefonoAndTercero(telefonoAAsignar, terceroAAsignar);
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

    @Transactional
    public void estadoTelefonosTerceroDeDireccion(TerceroDireccionEntity direccion, Boolean estado) {
        List<DireccionTelefonoEntity> relaciones = direccionTelefonoRepository.findByDireccionTerAndContacto(direccion, false);
        if (!relaciones.isEmpty()) {
            terceroTelefonoRepository.saveAll(relaciones.stream().map(item -> {
                        item.getTelefonoTer().setEstado(estado);
                        return item.getTelefonoTer();
                    }).toList()
            );
        }
    }

    public void eliminarRelacionDireccionYTercero(TerceroDireccionEntity relacionDireccionTercero) {
        List<DireccionTelefonoEntity> relaciones = direccionTelefonoRepository
                .findByDireccionTerAndContacto(relacionDireccionTercero, false);
        if (!relaciones.isEmpty()) {
            relaciones.forEach(item -> {
                direccionTelefonoRepository.delete(item);
                terceroTelefonoRepository.delete(item.getTelefonoTer());
            });
        }
    }

}
