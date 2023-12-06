package com.sigloV1.dao.repositories.relacionesMaM;

import com.sigloV1.dao.models.TerceroRolTipoTer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerceroRolTipoTerRepository extends JpaRepository<TerceroRolTipoTer,Long> {
}
