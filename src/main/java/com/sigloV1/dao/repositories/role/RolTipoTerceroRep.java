package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolTipoTerceroRep extends JpaRepository<RolTipoTerceroEntity,Long> {
    List<RolTipoTerceroEntity> findByTipoTercero(TipoTerceroEntity tipoTercero);
}
