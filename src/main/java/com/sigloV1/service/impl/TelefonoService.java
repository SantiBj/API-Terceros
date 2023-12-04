package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.DireccionTelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroTelefonoRepository;
import com.sigloV1.service.interfaces.ITelefonoService;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService,TelefonoAdapter {

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

    private TelefonoEntity obtenerTelefonoOException(Long id){
        return telefonoRepository.findById(id)
                .orElseThrow(()->new BadRequestCustom("El telefono no existe"));
    }

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

    public DireccionTelefonoEntity unionTelefonoDireccion(TerceroDireccionEntity direccion, TerceroTelefonoEntity telefono,Boolean contacto) {
        Optional<DireccionTelefonoEntity> relacion = direccionTelefonoRepository.findByDireccionTerAndTelefonoTer(direccion, telefono);
        return relacion.orElseGet(() -> direccionTelefonoRepository.save(
                DireccionTelefonoEntity.builder()
                        .telefonoTer(telefono)
                        .direccionTer(direccion)
                        .contacto(contacto)
                        .build()));
    }

    @Transactional
    public void desactivarTelefonosTerceroDeDireccion(TerceroDireccionEntity direccion){
        List<DireccionTelefonoEntity> relaciones =  direccionTelefonoRepository.findByDireccionTerAndContacto(direccion,false);
        if (!relaciones.isEmpty()) {
            terceroTelefonoRepository.saveAll(relaciones.stream().map(item -> {
                        item.getTelefonoTer().setEstado(false);
                        return item.getTelefonoTer();
                    }).toList()
            );
        }
    }

    @Override
    public void desactivarTelefonoTercero(Long idTelefono,Long idTercero){
        TelefonoEntity telefono = obtenerTelefonoOException(idTelefono);
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(idTercero);
        TerceroTelefonoEntity relacion = terceroTelefonoRepository.findByTelefonoAndTercero(
                telefono,
                tercero
        ).orElseThrow(()-> new BadRequestCustom("El telefono no esta relacionado"));
        relacion.setEstado(false);
        terceroTelefonoRepository.save(relacion);
    }

    @Override
    public void eliminarRelacionDireccionYTercero(TerceroDireccionEntity relacionDireccionTercero){
        List<DireccionTelefonoEntity> relaciones = direccionTelefonoRepository
                .findByDireccionTerAndContacto(relacionDireccionTercero,false);
        if (!relaciones.isEmpty()){
            relaciones.forEach(item->{
                direccionTelefonoRepository.delete(item);
                terceroTelefonoRepository.delete(item.getTelefonoTer());
            });
        }
    }

    //lista de telefonos de un tercero
}
