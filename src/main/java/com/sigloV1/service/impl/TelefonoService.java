package com.sigloV1.service.impl;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroTelefonoEntity;
import com.sigloV1.dao.repositories.TelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroTelefonoRepository;
import com.sigloV1.service.interfaces.ITelefonoService;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.telefono.TerceroTelefonoResDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService {

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private TerceroTelefonoRepository terceroTelefonoRepository;

    @Autowired
    private ModelMapper modelMapper;


    ///corregir logica
    @Override
    public <T> TerceroTelefonoResDTO crearTelefonoUnionTercero(TelefonoReqDTO telefono, T tercero) {
        Optional<TelefonoEntity> telefonoExistente = telefonoRepository.findByNumero(telefono.getNumero());
        if (tercero instanceof TerceroEntity){
            if (telefonoExistente.isEmpty()){
                TelefonoEntity nuevoTelefono = telefonoRepository.save(TelefonoEntity.builder()
                    .numero(telefono.getNumero())
                    .tipoTelefono(telefono.getTipoTelefono())
                    .extension(telefono.getExtension())
                    .build());
                TerceroTelefonoEntity relacion = terceroTelefonoRepository.save(
                        TerceroTelefonoEntity.builder()
                                .tercero((TerceroEntity) tercero)
                                .telefono(nuevoTelefono)
                                .build()
                );
                return modelMapper.map(relacion, TerceroTelefonoResDTO.class);
            }else{
                return modelMapper.map(terceroTelefonoRepository.save(
                        TerceroTelefonoEntity.builder()
                                .tercero((TerceroEntity) tercero)
                                .telefono(telefonoExistente.get())
                                .build()
                ), TerceroTelefonoResDTO.class);
            }
        }else{
            if (telefonoExistente.isEmpty()){
                TelefonoEntity nuevoTelefono = telefonoRepository.save(TelefonoEntity.builder()
                        .numero(telefono.getNumero())
                        .tipoTelefono(telefono.getTipoTelefono())
                        .extension(telefono.getExtension())
                        .build());
                TerceroTelefonoEntity relacion = terceroTelefonoRepository.save(
                        TerceroTelefonoEntity.builder()
                                .tercero((TerceroEntity) tercero)
                                .telefono(nuevoTelefono)
                                .build()
                );
                return modelMapper.map(relacion, TerceroTelefonoResDTO.class);
            }else{
                return modelMapper.map(terceroTelefonoRepository.save(
                        TerceroTelefonoEntity.builder()
                                .tercero((TerceroEntity) tercero)
                                .telefono(telefonoExistente.get())
                                .build()
                ), TerceroTelefonoResDTO.class);
            }
        }


        return ;
    }
}
