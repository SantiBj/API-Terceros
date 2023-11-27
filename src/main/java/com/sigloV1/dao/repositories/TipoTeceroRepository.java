package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.TipoTerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoTeceroRepository extends JpaRepository<TipoTerceroEntity,Long> {
    Optional<TipoTerceroEntity> findByNombreIgnoreCase(String nombre);
}
