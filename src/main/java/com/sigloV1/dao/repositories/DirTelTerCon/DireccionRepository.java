package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.DireccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionEntity,Long> {
    Optional<DireccionEntity> findByDireccionIgnoreCase(String direccion);

}
