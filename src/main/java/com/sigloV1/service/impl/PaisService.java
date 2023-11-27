package com.sigloV1.service.impl;

import com.sigloV1.dao.models.PaisEntity;
import com.sigloV1.dao.repositories.PaisRepository;
import com.sigloV1.service.interfaces.IPaisService;
import com.sigloV1.service.interfaces.adapters.PaisAdapter;
import com.sigloV1.web.dtos.req.pais.PaisDTOReq;
import com.sigloV1.web.dtos.req.pais.PaisDTOReqBi;
import com.sigloV1.web.dtos.res.pais.PaisDTORes;
import com.sigloV1.web.dtos.res.pais.PaisDTOResBi;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaisService implements IPaisService, PaisAdapter {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ModelMapper modelMapper;

    //helpers
    public PaisEntity obtenerPaisOException(Long id) {
        return paisRepository.findById(id)
                .orElseThrow(() -> new BadRequestCustom("Pais no encontrado"));
    }

    public boolean paisIsExists(String nombre) {
        return paisRepository.existsByNombrePaisIgnoreCase(nombre);
    }

    //helpers

    @Override
    public List<PaisDTORes> obtenerPaises() {
        List<PaisDTORes> paises = paisRepository.findByEstado(true).stream()
                .map(pais -> modelMapper.map(pais, PaisDTORes.class))
                .toList();

        if (paises.isEmpty()) {
            return new ArrayList<PaisDTORes>();
        } else {
            return paises;
        }
    }

    @Override
    public PaisDTOResBi obtenerPais(Long id) {
        PaisEntity pais = obtenerPaisOException(id);
        if (!pais.getEstado()) {
            throw new BadRequestCustom("El pais esta desactivado");
        }
        return modelMapper.map(pais, PaisDTOResBi.class);
    }

    @Override
    @Transactional
    public PaisDTOResBi crearPaisBi(PaisDTOReqBi pais) {
        if (!paisIsExists(pais.getNombrePais())) {
            PaisEntity paisEntity = modelMapper.map(pais, PaisEntity.class);
            paisEntity.setEstado(true);
            paisEntity.getEstados()
                    .forEach((estado) -> {
                        estado.setPais(paisEntity);
                        estado.getCiudades()
                                .forEach(ciudad -> ciudad.setEstado(estado));
                    });
            return modelMapper.map(paisRepository.save(paisEntity), PaisDTOResBi.class);
        } else {
            throw new BadRequestCustom("El pais " + pais.getNombrePais() + " ya existe.");
        }
    }


    @Override
    public PaisDTORes crearPais(PaisDTOReq pais) {
        if (!paisIsExists(pais.getNombrePais())) {
            PaisEntity paisEntity = modelMapper.map(pais, PaisEntity.class);
            paisEntity.setEstado(true);
            return modelMapper.map(paisRepository.save(paisEntity), PaisDTORes.class);
        } else {
            throw new BadRequestCustom("El pais " + pais.getNombrePais() + " ya existe.");
        }
    }

    public PaisDTOResBi editarPais(PaisDTOReq pais,Long id){
        PaisEntity paisEntity = obtenerPaisOException(id);
        if (pais.getNombrePais() != null){
            paisEntity.setNombrePais(pais.getNombrePais());
        }
        if (pais.getIndicativo() != null){
            paisEntity.setIndicativo(pais.getIndicativo());
        }

        return modelMapper.map(paisRepository.save(paisEntity),PaisDTOResBi.class);
    }


    @Override
    public PaisDTORes desactivarPais(Long id) {
        PaisEntity pais = obtenerPaisOException(id);
        if (!pais.getEstado()) {
            throw new BadRequestCustom("El pais ya esta desactivado");
        } else {
            pais.setEstado(false);
            return modelMapper.map(paisRepository.save(pais), PaisDTORes.class);
        }
    }

    @Override
    public List<PaisDTORes> paisesDesactivados() {
        List<PaisDTORes> paises = paisRepository.findByEstado(false)
                .stream().map(pais -> modelMapper.map(pais, PaisDTORes.class)).toList();

        if (paises.isEmpty()) {
            return new ArrayList<PaisDTORes>();
        } else {
            return paises;
        }
    }
}
