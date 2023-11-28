package com.sigloV1.service.impl;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.DireccionTelefonoEntity;
import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.DireccionTelefonoRepository;
import com.sigloV1.service.interfaces.IDireccionesService;
import com.sigloV1.service.interfaces.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.adapters.DireccionAdapter;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTOTel;
import com.sigloV1.web.dtos.res.direccion.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DireccionService implements IDireccionesService, DireccionAdapter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DireccionTelefonoRepository direccionTelefonoRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TelefonoAdapter telefonoAdapter;

    @Autowired
    private CiudadAdapter ciudadAdapter;

    public DireccionEntity obtenerDireccionOException(Long id) {
        DireccionEntity direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("La direccion no existe"));

        if (!direccion.getCiudad().getEstado().getPais().getEstado()) {
            throw new BadRequestCustom("La direccion existe pero el al pais al que pertenece se encuentra desactivado");
        } else {
            return direccion;
        }
    }

    @Override
    public DireccionResDTO obtenerDireccion(Long id) {
        DireccionEntity direccion = obtenerDireccionOException(id);
        return DireccionResDTO.builder()
                .id(direccion.getId())
                .nombre(direccion.getNombre())
                .direccion(direccion.getDireccion())
                .codigoPostal(direccion.getCodigoPostal())
                .ciudad(direccion.getCiudad().getId())
                .build();
    }


    @Override
    public DireccionResDTOTel obtenerDireccionConTelefonos(Long id) {
        DireccionEntity direccion = obtenerDireccionOException(id);
        return DireccionResDTOTel.builder()
                .id(direccion.getId())
                .nombre(direccion.getNombre())
                .direccion(direccion.getDireccion())
                .codigoPostal(direccion.getCodigoPostal())
                .ciudad(direccion.getCiudad().getId())
                .telefonos(direccion.getTelefonos().stream().map(t -> modelMapper.map(t, TelefonoResDTO.class)).toList())
                .build();
    }

    @Transactional
    public void saveTelefonosInDireccion(List<DireccionTelefonoEntity> relaciones) {
        direccionTelefonoRepository.saveAll(relaciones);
    }


    @Override
    public DireccionResDTO crearDireccion(DireccionReqDTO direccion) {
        DireccionEntity newDireccion = DireccionEntity.builder()
                .nombre(direccion.getNombre())
                .direccion(direccion.getDireccion())
                .codigoPostal(direccion.getCodigoPostal())
                .ciudad(ciudadAdapter.obtenerCiudadOException(direccion.getCiudad()))
                .build();

        newDireccion = direccionRepository.save(newDireccion);

        return DireccionResDTO.builder()
                .id(newDireccion.getId())
                .nombre(newDireccion.getNombre())
                .direccion(newDireccion.getDireccion())
                .codigoPostal(newDireccion.getCodigoPostal())
                .ciudad(newDireccion.getCiudad().getId())
                .build();
    }

    
    public void creacionDireccionesAndTelefonosTercero(List<DireccionTelefonosReqDTO> direccionesAndTelefonos,TerceroEntity tercero) {
        direccionesAndTelefonos.forEach(item -> {
            Optional<DireccionEntity> oldDireccion = direccionRepository.findByDireccionIgnoreCase(item.getDireccion());
            if (oldDireccion.isEmpty()){
                Long newDireccion = CreateAndRelationsDireccion(modelMapper.map(item,DireccionReqDTO.class),tercero);
                if (!item.getTelefonos().isEmpty()) {
                    CreateAndRelationsTelefonos(item.getTelefonos(),newDireccion,tercero);
                }
            }else{
                if (!item.getTelefonos().isEmpty()) {
                    CreateAndRelationsTelefonos(item.getTelefonos(),oldDireccion.get().getId(),tercero);
                }

            }
        });
    }

    private Long CreateAndRelationsDireccion(DireccionReqDTO direccion,TerceroEntity tercero){
        DireccionEntity newDireccion = DireccionEntity.builder()
                .nombre(direccion.getNombre())
                .direccion(direccion.getDireccion())
                .codigoPostal(direccion.getCodigoPostal())
                .ciudad(ciudadAdapter.obtenerCiudadOException(direccion.getCiudad()))
                .build();
        newDireccion = direccionRepository.save(newDireccion);
        /* ADD direccion tercero TODO */
        return newDireccion.getId();
    }

    private void CreateAndRelationsTelefonos(List<TelefonoReqDTO> telefonos, Long direccion, TerceroEntity tercero ){
        List<TelefonoEntity> newTelefonos = telefonoAdapter.crearTelefonosEntity(telefonos);
        telefonoAdapter.addTelefonosDireccion(direccion,newTelefonos);
        telefonoAdapter.addTelefonoTercero(newTelefonos,tercero);
    }

    @Override
    public DireccionResDTO editarDireccion(DireccionReqDTO direccion, Long id) {
        DireccionEntity oldDireccion = obtenerDireccionOException(id);
        oldDireccion.setNombre(direccion.getNombre());
        oldDireccion.setDireccion(direccion.getDireccion());
        oldDireccion.setCodigoPostal(direccion.getCodigoPostal());
        oldDireccion.setCiudad(!Objects.equals(direccion.getCiudad(), oldDireccion.getCiudad().getId()) ? ciudadAdapter.obtenerCiudadOException(direccion.getCiudad()) : oldDireccion.getCiudad());

        oldDireccion = direccionRepository.save(oldDireccion);
        return DireccionResDTO.builder()
                .id(oldDireccion.getId())
                .nombre(oldDireccion.getNombre())
                .direccion(oldDireccion.getDireccion())
                .codigoPostal(oldDireccion.getCodigoPostal())
                .ciudad(oldDireccion.getCiudad().getId())
                .build();
    }

    @Override
    public void eliminarDireccion(Long id) {
        DireccionEntity direccion = obtenerDireccionOException(id);
        direccionRepository.delete(direccion);
    }


}
