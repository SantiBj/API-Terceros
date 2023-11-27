package com.sigloV1.service.interfaces;

import com.sigloV1.web.dtos.req.TipoTerceroDTORq;
import com.sigloV1.web.dtos.res.TipoTerceroDTORs;

import java.util.List;

public interface ITipoTerceroService {

    List<TipoTerceroDTORs> listaTipoTerceros();
    TipoTerceroDTORs crearTipoTercero(TipoTerceroDTORq tipoTercero);

    void desactivarTipoTercero(Long id);

    TipoTerceroDTORs activarTipoTercero(Long id);

    TipoTerceroDTORs editarTipoTercero(TipoTerceroDTORq tipoTercero,Long id);
}
