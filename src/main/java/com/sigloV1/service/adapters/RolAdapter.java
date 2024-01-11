package com.sigloV1.service.adapters;

import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;

public interface RolAdapter {
    <T> TerceroRolTipoTerEntity relacionarTerceroRol(T tercero, Long fullRol);
    RolTipoTerceroEntity obtenerRolTipoTerceroOException(Long relacionId);
}
