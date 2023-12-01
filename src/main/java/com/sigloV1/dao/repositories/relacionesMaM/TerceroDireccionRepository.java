package com.sigloV1.dao.repositories.relacionesMaM;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerceroDireccionRepository extends JpaRepository<TerceroDireccionEntity,Long> {
    Optional<TerceroDireccionEntity> findByDireccionAndTercero(DireccionEntity direccion, TerceroEntity tercero);
}
