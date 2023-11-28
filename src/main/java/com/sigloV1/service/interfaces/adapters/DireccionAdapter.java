package com.sigloV1.service.interfaces.adapters;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.DireccionTelefonoEntity;

import java.util.List;

public interface DireccionAdapter {
    DireccionEntity obtenerDireccionOException(Long id);
    void saveTelefonosInDireccion(List<DireccionTelefonoEntity> relaciones);
}
