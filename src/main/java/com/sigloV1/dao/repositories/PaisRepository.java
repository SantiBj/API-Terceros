package com.sigloV1.dao.repositories;


import com.sigloV1.dao.models.PaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaisRepository extends JpaRepository<PaisEntity,Long> {
    List<PaisEntity> findByEstado(Boolean estado);

    Boolean existsByNombrePaisIgnoreCase(String nombrePais);
}
