package com.sigloV1.service.interfaces.adapters;

import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTer;

public interface TerceroAdapter {
    TerceroEntity obtenerTerceroOException(Long id);
    TerceroRolTipoTer obtenerRolTercero(Long id);
}
