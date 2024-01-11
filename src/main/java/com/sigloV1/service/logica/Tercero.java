package com.sigloV1.service.logica;

import com.sigloV1.dao.models.DocDetallesEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;
import com.sigloV1.dao.repositories.DocDetallesRep;
import com.sigloV1.dao.repositories.tercero.TerceroRepository;
import com.sigloV1.dao.repositories.tercero.TerceroRolTipoTerRepository;
import com.sigloV1.service.adapters.PaisAdapter;
import com.sigloV1.service.adapters.TerceroAdapter;
import com.sigloV1.service.adapters.TipoTerceroAdapter;
import com.sigloV1.web.dtos.req.tercero.TerceroReqDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Tercero implements TerceroAdapter {

    @Autowired
    private DocDetallesRep docDetallesRep;

    @Autowired
    private PaisAdapter paisAdapter;

    @Autowired
    private TipoTerceroAdapter tipoTerceroAdapter;

    @Autowired
    private TerceroRepository terceroRepository;

    @Autowired
    private TerceroRolTipoTerRepository terceroRol;

    @Override
    public TerceroEntity obtenerTerceroOException(Long id) {
        return terceroRepository.findById(id)
                .orElseThrow(()->new BadRequestCustom("El tercero no existe"));
    }

    //busca por el id de la relacion
    public TerceroRolTipoTerEntity obtenerRolTercero(Long id){
        return terceroRol.findById(id)
                .orElseThrow(()->new BadRequestCustom("El tercero no tiene el rol indicado"));
    }

    public TerceroEntity crearTercero(TerceroReqDTO data){
        DocDetallesEntity docDetalles = docDetallesRep.findById(data.getDocDetalles())
                .orElseThrow(()->new BadRequestCustom("El tipo de documento no existe."));

        Boolean alreadyExistsTercero = terceroRepository.terceroExistente(
                data.getIdentificacion(),
                docDetalles,
                data.getNombreComercial(),
                data.getRazonSocial()
        ).isPresent();

        if (alreadyExistsTercero) throw new BadRequestCustom("El tercero ya existe");

        return terceroRepository.save(TerceroEntity.builder()
                .identificacion(data.getIdentificacion())
                .nombre(data.getNombre())
                .razonSocial(data.getRazonSocial())
                .nombreComercial(data.getNombreComercial())
                .pais(paisAdapter.obtenerPaisOException(data.getPais()))
                .docDetalles(docDetalles)
                .terceroPadre(
                        data.getTerceroPadre() != null
                                ? obtenerTerceroOException(data.getTerceroPadre())
                                : null
                )
                .tipoTercero(tipoTerceroAdapter
                        .obtenerTerceroOException(data.getTipoTercero()))
                .fechaExpedicion(data.getFechaExpedicion())
                .build());
    }
}
