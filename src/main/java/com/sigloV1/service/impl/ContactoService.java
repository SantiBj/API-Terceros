package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.contacto.CargoRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.contacto.IContactoService;
import com.sigloV1.service.logica.contacto.MetodosContacto;
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

    @Autowired
    private MetodosContacto metodosContacto;


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
    public List<ContactoResDTO> contactosTercero(Long terceroId) {
        List<ContactoEntity> contactos = contactoRepository.findByTercero(terceroAdapter
                .obtenerTerceroOException(terceroId));
        return contactos.stream().map(c->{
            return ContactoResDTO
                    .builder()
                    .idContactoTer(c.getContacto().getId())
                    .contactoRelacionId(c.getId())
                    .identificacion(c.getContacto().getIdentificacion())
                    .nombre(c.getContacto().getNombre())
                    .estado(c.getEstado())
                    .build();
        }).toList();
    }

    @Override
    public void eliminarContacto(Long relacionId) {
        ContactoEntity contacto = metodosContacto.obtenerContactoOException(relacionId);
        contactoRepository.delete(contacto);
    }

    @Override
    public void estadoContacto(Long relacionId,Boolean estado) {
        ContactoEntity relacion = metodosContacto.obtenerContactoOException(relacionId);
        relacion.setEstado(estado);
        contactoRepository.save(relacion);
    }
}
