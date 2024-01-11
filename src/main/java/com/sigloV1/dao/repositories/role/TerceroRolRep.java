package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerceroRolRep extends JpaRepository<TerceroRolTipoTerEntity,Long> {

    Optional<TerceroRolTipoTerEntity> findByTerceroAndRol(TerceroEntity tercero, RolTipoTerceroEntity rol);

}
