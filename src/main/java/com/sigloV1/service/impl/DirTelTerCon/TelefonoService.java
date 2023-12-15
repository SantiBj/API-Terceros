package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.TelefonoRepository;
import com.sigloV1.service.interfaces.direccionesTelefonos.ITelefonoService;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelefonoService implements ITelefonoService {

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TelefonoEntity crearTelefono(TelefonoReqDTO dataTelefono) {
        return telefonoRepository.findByNumero(dataTelefono.getNumero()).orElseGet(()->
                telefonoRepository.save(modelMapper.map(dataTelefono,TelefonoEntity.class))
                );
    }
}
