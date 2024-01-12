package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.web.dtos.req.TipoTerceroDTORq;

import java.util.List;

public interface ITipoTerceroService {

    List<TipoTerceroEntity> listaTipoTerceros();
    void crearTipoTercero(TipoTerceroDTORq tipoTercero);

    void estadoTipoTercero(Long tipoTerceroId,Boolean estado);

    TipoTerceroEntity editarTipoTercero(TipoTerceroDTORq tipoTercero,Long id);
}
