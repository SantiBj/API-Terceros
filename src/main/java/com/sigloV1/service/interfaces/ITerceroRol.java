package com.sigloV1.service.interfaces;

import com.sigloV1.dao.models.TerceroRolTipoTerEntity;

public interface ITerceroRol {
    <T> TerceroRolTipoTerEntity relacionarTerceroRol(T tercero, Long fullRol );
    void estadoTerceroRol(Long relacionTerceroRolId,Boolean estado);
}
