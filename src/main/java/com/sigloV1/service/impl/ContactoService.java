package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.contacto.CargoRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.contacto.IContactoService;
import com.sigloV1.web.dtos.req.contacto.ContactoReqDTO;
import com.sigloV1.web.dtos.res.contacto.ContactoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService implements IContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public ContactoResDTO crearContacto(ContactoReqDTO dataContacto) {
        if (dataContacto.getContactoId().equals(dataContacto.getTercero())) throw new BadRequestCustom("Un tercero no puede ser contacto de si mismo.");
        TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(dataContacto.getTercero());
        TerceroEntity contacto = terceroAdapter.obtenerTerceroOException(dataContacto.getContactoId());
        CargoEntity cargo = cargoRepository.findById(dataContacto.getCargo())
                .orElseThrow(()->new BadRequestCustom("El cargo no existe."));

        if (contactoRepository.findByContactoAndTerceroAndCargo(contacto,tercero,cargo).isPresent()){
            throw new BadRequestCustom("El contacto ya existe.");
        }

        ContactoEntity contactoRelacion = contactoRepository.save(ContactoEntity
                .builder()
                        .contacto(contacto)
                        .tercero(tercero)
                        .cargo(cargo)
                        .estado(true)
                .build());
        return ContactoResDTO.builder()
                .contactoRelacionId(contactoRelacion.getId())
                .idContactoTer(contactoRelacion.getContacto().getId())
                .identificacion(contactoRelacion.getContacto().getIdentificacion())
                .nombre(contactoRelacion.getContacto().getNombre())
                .build();
    }

    @Override
    public List<ContactoResDTO> contactosTercero(Long idTercero) {
        return null;
    }

    @Override
    public void eliminarContacto(Long relacionId) {

    }

    @Override
    public void estadoContacto(Long relacionId) {

    }
}
