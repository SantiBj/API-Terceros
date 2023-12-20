package com.sigloV1.service.impl.DirTelTerCon;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.repositories.DirTelTerCon.TelefonoRepository;
import com.sigloV1.service.impl.DirTelTerCon.returnMethods.ReturnCustomTelefono;
import com.sigloV1.service.interfaces.direccionesTelefonos.ITelefonoService;
import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService {

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReturnCustomTelefono crearTelefono(TelefonoReqDTO dataTelefono) {

        Optional<TelefonoEntity> telefonoExistente = telefonoRepository
                .findByNumero(dataTelefono.getNumero());

        if (telefonoExistente.isPresent()){
            return ReturnCustomTelefono.builder()
                    .telefono(telefonoExistente.get())
                    .alReadyExisted(true)
                    .build();
        }else{
            return ReturnCustomTelefono.builder()
                    .telefono(telefonoRepository.save(TelefonoEntity.builder()
                                    .numero(dataTelefono.getNumero())
                                    .tipoTelefono(dataTelefono.getTipoTelefono())
                            .build()))
                    .alReadyExisted(false)
                    .build();
        }
    }
}
