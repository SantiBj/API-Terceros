package com.sigloV1.service.adapters;

import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;

public interface TerceroAdapter {
    TerceroEntity obtenerTerceroOException(Long id);
    TerceroRolTipoTerEntity obtenerRolTercero(Long id);
}
