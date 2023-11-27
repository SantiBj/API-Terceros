package com.sigloV1.service.impl;


import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.dao.repositories.TipoTeceroRepository;
import com.sigloV1.service.interfaces.ITipoTerceroService;
import com.sigloV1.service.utils.TipoTerceroUtil;
import com.sigloV1.web.dtos.req.TipoTerceroDTORq;
import com.sigloV1.web.dtos.res.TipoTerceroDTORs;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoTerceroServiceImpl implements ITipoTerceroService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TipoTeceroRepository tipoTeceroRepository;

    private TipoTerceroUtil obtenerTipoTercero(String nombre) {
        TipoTerceroEntity tercero = tipoTeceroRepository.findByNombreIgnoreCase(nombre).orElse(new TipoTerceroEntity());

        return TipoTerceroUtil.builder()
                .tercero(tercero)
                .existe(tercero.getNombre() != null)
                .build();
    }

    private TipoTerceroEntity obtenerTerceroOException(Long id) {
        return tipoTeceroRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("El tipo de tercero no existe"));
    }

    @Override
    public List<TipoTerceroDTORs> listaTipoTerceros() {
        List<TipoTerceroEntity> tipos = tipoTeceroRepository.findAll();
        if (!tipos.isEmpty()) {
            return tipos.stream()
                    .filter(tipo -> tipo.getEstado())
                    .map(tipo -> modelMapper.map(tipo, TipoTerceroDTORs.class))
                    .toList();
        } else {
            return new ArrayList<TipoTerceroDTORs>();
        }
    }


    public TipoTerceroEntity cambiarEstadoTipoTercero(TipoTerceroEntity tipoTercero) {
        tipoTercero.setEstado(true);
        return tipoTeceroRepository.save(tipoTercero);
    }

    public TipoTerceroDTORs activarTipoTercero(Long id) {
        TipoTerceroEntity tipo = obtenerTerceroOException(id);
        return modelMapper.map(cambiarEstadoTipoTercero(tipo), TipoTerceroDTORs.class);
    }

    //si el tercero ya existe se envia el existente, de lo contrario se crea y envia el nuevo
    @Override
    public TipoTerceroDTORs crearTipoTercero(TipoTerceroDTORq tipoTercero) {
        TipoTerceroUtil infoTercero = obtenerTipoTercero(tipoTercero.getNombre());
        if (infoTercero.getExiste()) {
            if (!infoTercero.getTercero().getEstado()) {
                cambiarEstadoTipoTercero(infoTercero.getTercero());
            }
            return modelMapper.map(infoTercero.getTercero(), TipoTerceroDTORs.class);
        } else {
            TipoTerceroEntity nuevoTercero = modelMapper.map(tipoTercero, TipoTerceroEntity.class);
            nuevoTercero.setEstado(true);
            return modelMapper
                    .map(tipoTeceroRepository.save(nuevoTercero), TipoTerceroDTORs.class);
        }
    }

    @Override
    public void desactivarTipoTercero(Long id) {
        TipoTerceroEntity tipo = obtenerTerceroOException(id);
        if (tipo.getEstado()) {
            tipo.setEstado(false);
            tipoTeceroRepository.save(tipo);
        }
    }

    @Override
    public TipoTerceroDTORs editarTipoTercero(TipoTerceroDTORq tipoTercero, Long id) {
        TipoTerceroEntity tipo = obtenerTerceroOException(id);
        if (tipo.getEstado()) {
            tipo.setNombre(tipoTercero.getNombre());
            return modelMapper.map(tipoTeceroRepository.save(tipo), TipoTerceroDTORs.class);
        } else {
            throw new BadRequestCustom("El tipo de tercero se encuentra desactivado activelo para poder editarlo");
        }
    }
}
