package com.sigloV1.service.impl;

import com.sigloV1.dao.models.*;
import com.sigloV1.dao.repositories.DireccionRepository;
import com.sigloV1.dao.repositories.relacionesMaM.DireccionTelefonoRepository;
import com.sigloV1.dao.repositories.relacionesMaM.TerceroDireccionRepository;
import com.sigloV1.service.interfaces.IDireccionService;
import com.sigloV1.service.interfaces.adapters.CiudadAdapter;
import com.sigloV1.service.interfaces.adapters.TelefonoAdapter;
import com.sigloV1.service.interfaces.adapters.TerceroAdapter;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionResDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionTelefonosResDTO;
import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService implements IDireccionService {

    @Autowired
    private CiudadAdapter ciudadAdapter;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TerceroDireccionRepository terceroDireccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TerceroAdapter terceroAdapter;

    @Autowired
    private TelefonoAdapter telefonoAdapter;

    @Autowired
    private DireccionTelefonoRepository direccionTelefonoRepository;

    private DireccionEntity obtenerDireccionEntityOException(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("La direccion no existe."));
    }

    private DireccionEntity creacionDireccion(DireccionReqDTO direccion) {
        Optional<DireccionEntity> direccionExistente = direccionRepository
                .findByDireccionIgnoreCase(direccion.getDireccion());
        if (direccionExistente.isEmpty()) {
            DireccionEntity newDireccion = DireccionEntity.builder()
                    .nombre(direccion.getNombre())
                    .direccion(direccion.getDireccion())
                    .codigoPostal(direccion.getCodigoPostal())
                    .ciudad(ciudadAdapter.obtenerCiudadOException(direccion.getCiudad()))
                    .build();
            return direccionRepository.save(newDireccion);
        } else {
            return direccionExistente.get();
        }
    }


    public TerceroDireccionEntity crearDireccionUnionTercero(DireccionReqDTO direccion, TerceroEntity tercero) {
        DireccionEntity direccionEntity = creacionDireccion(direccion);
        Optional<TerceroDireccionEntity> relacionExistente = terceroDireccionRepository
                .findByDireccionAndTercero(direccionEntity, tercero);
        return relacionExistente.orElseGet(() -> terceroDireccionRepository
                .save(TerceroDireccionEntity.builder()
                        .tercero(tercero)
                        .direccion(direccionEntity)
                        .estado(true)
                        .build()
                )
        );
    }


    @Transactional
    public List<DireccionTelefonoEntity> crearDireccionTelefonoUnionTercero(DireccionTelefonosReqDTO direccionTelefonos, TerceroEntity tercero) {
        TerceroDireccionEntity direccion = crearDireccionUnionTercero(modelMapper.map(direccionTelefonos, DireccionReqDTO.class), tercero);
        return direccionTelefonos.getTelefonos().stream().map(
                telefono -> telefonoAdapter.unionTelefonoDireccion(
                        direccion,
                        telefonoAdapter.crearTelefonoUnionTercero(telefono, tercero),
                        false
                )
        ).toList();
    }

    //puede ser llamado desde creacion tercero
    //se puede crear solo con el endpoint direccion-telefonos o solo direccion
    @Override
    public <D, T> void crearDireccionSegunCorresponda(D direccion, T tercero) {
        //conviertiendo el tercero a entidad
        TerceroEntity terceroEntity;
        if (tercero instanceof TerceroEntity) {
            terceroEntity = (TerceroEntity) tercero;
        } else if (tercero instanceof Long) {
            terceroEntity = terceroAdapter.obtenerTerceroOException((Long) tercero);
        } else {
            throw new BadRequestCustom("La tercero no cumple con el formato indicado");
        }

        if (direccion instanceof DireccionReqDTO) {
            crearDireccionUnionTercero((DireccionReqDTO) direccion, terceroEntity);
        } else if (direccion instanceof DireccionTelefonosReqDTO) {
            crearDireccionTelefonoUnionTercero((DireccionTelefonosReqDTO) direccion, terceroEntity);
        } else {
            throw new BadRequestCustom("La direccion no cumple con el formato indicado");
        }
    }

    @Override
    public void desactivarDireccionTercero(Long id, Long idTercero) {
        try {
            DireccionEntity direccion = obtenerDireccionEntityOException(id);
            TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(idTercero);
            TerceroDireccionEntity relacion = terceroDireccionRepository
                    .findByDireccionAndTercero(direccion, tercero)
                    .orElseThrow(() -> new BadRequestCustom("La direccion no se encuentra relaciona"));

            relacion.setEstado(false);
            relacion = terceroDireccionRepository.save(relacion);
            telefonoAdapter.desactivarTelefonosTerceroDeDireccion(relacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestCustom("Ocurrio un error al desactivar la direccion del tercero y los telefonos asociados.");
        }
    }

    @Override
    public void eliminarDireccion(Long idDireccion, Long idTercero) {
        try {
            TerceroEntity tercero = terceroAdapter.obtenerTerceroOException(idTercero);
            TerceroDireccionEntity relacionDireccionTercero = terceroDireccionRepository
                    .findByDireccionAndTercero(obtenerDireccionEntityOException(idDireccion), tercero)
                    .orElseThrow(() -> new BadRequestCustom("La direccion no tiene relacion con el tercero indicado."));
            telefonoAdapter.eliminarRelacionDireccionYTercero(relacionDireccionTercero);
            terceroDireccionRepository.delete(relacionDireccionTercero);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestCustom("Ocurrio un error al intentar eliminar la relacion entre la direccion , el tercero y los telefonos.");
        }

    }

    @Override
    public DireccionResDTO editarDireccion(DireccionReqDTO datos, Long idDireccion) {
        DireccionEntity direccion = obtenerDireccionEntityOException(idDireccion);
        direccion.setNombre(datos.getNombre());
        direccion.setDireccion(datos.getDireccion());
        direccion.setCodigoPostal(datos.getCodigoPostal());
        direccion.setCiudad(ciudadAdapter.obtenerCiudadOException(datos.getCiudad()));
        return modelMapper.map(direccionRepository.save(direccion), DireccionResDTO.class);
    }

    @Override
    public List<DireccionTelefonosResDTO> obtenerDireccionesPorTercero(Long idTercero) {
        List<TerceroDireccionEntity> direcciones = terceroDireccionRepository
                .findByTercero(terceroAdapter.obtenerTerceroOException(idTercero));
        return direcciones.stream().map(
                item -> DireccionTelefonosResDTO.builder()
                        .id(item.getDireccion().getId())
                        .nombre(item.getDireccion().getNombre())
                        .direccion(item.getDireccion().getDireccion())
                        .codigoPostal(item.getDireccion().getCodigoPostal())
                        .ciudad(item.getDireccion().getCiudad().getId())
                        .estado(item.getEstado())
                        .telefonos(item.getTelefonos().stream()
                                .map(telefono -> (
                                        TelefonoResDTO.builder()
                                                .id(telefono.getTelefonoTer().getTelefono().getId())
                                                .numero(telefono.getTelefonoTer().getTelefono().getNumero())
                                                .tipoTelefono(telefono.getTelefonoTer().getTelefono().getTipoTelefono())
                                                .extension(telefono.getTelefonoTer().getTelefono().getExtension())
                                                .estado(telefono.getTelefonoTer().getEstado())
                                                .build()
                                )).toList())
                        .build()
        ).toList();
    }
}
