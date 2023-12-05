package com.sigloV1.dao.repositories.relacionesMaM;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TerceroTelefonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerceroTelefonoRepository extends JpaRepository<TerceroTelefonoEntity,Long> {

    Optional<TerceroTelefonoEntity> findByTelefonoAndTercero(TelefonoEntity telefono, TerceroEntity tercero);

    List<TerceroTelefonoEntity> findByTercero(TerceroEntity tercero);
}
