package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.contacto.CargoRepository;
import com.sigloV1.dao.repositories.contacto.ContactoRepository;
import com.sigloV1.service.adapters.ContactoAdapter;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.interfaces.contacto.IContactoService;
import com.sigloV1.service.logica.contacto.MetodosContacto;
import com.sigloV1.web.dtos.req.contacto.ContactoReqDTO;
import com.sigloV1.web.dtos.req.contacto.ContactoReqEntity;
import com.sigloV1.web.dtos.res.contacto.ContactoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService implements IContactoService, ContactoAdapter {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private TerceroAdapter validacionExistencia;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private MetodosContacto metodosContacto;


    @Override
    public <T> ContactoEntity crearContacto(T dataContacto) {

        TerceroEntity terceroEntity;
        TerceroEntity contacto;
        Long cargoId;

        if (dataContacto instanceof ContactoReqDTO){
            ContactoReqDTO dataExterna = (ContactoReqDTO) dataContacto;
            terceroEntity = validacionExistencia.obtenerTerceroOException(dataExterna.getTercero());
            contacto = validacionExistencia.obtenerTerceroOException(dataExterna.getContactoId());
            cargoId = dataExterna.getCargo();
        }else{
            ContactoReqEntity entidades = (ContactoReqEntity) dataContacto;
            terceroEntity = entidades.getTercero();
            contacto = entidades.getContacto();
            cargoId = entidades.getCargo();
        }

        CargoEntity cargo = cargoRepository.findById(cargoId)
                .orElseThrow(()->new BadRequestCustom("El cargo no existe."));

        if (terceroEntity.getId().equals(contacto.getId())){
            throw new BadRequestCustom("Un tercero no puede ser contacto de si mismo.");
        }
        if (!contacto.getTipoTercero().getNombre().toUpperCase().equals("NATURAL")){
            throw new BadRequestCustom("El contacto debe ser una persona natural.");
        }
        if (contactoRepository.findByContactoAndTerceroAndCargo(contacto,terceroEntity,cargo).isPresent()){
            throw new BadRequestCustom("El contacto ya existe.");
        }

        return contactoRepository.save(ContactoEntity
                .builder()
                .contacto(contacto)
                .tercero(terceroEntity)
                .cargo(cargo)
                .estado(true)
                .build());
    }

    @Override
    public List<ContactoResDTO> contactosTercero(Long terceroId) {
        List<ContactoEntity> contactos = contactoRepository.findByTercero(validacionExistencia
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
