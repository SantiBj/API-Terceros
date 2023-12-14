package com.sigloV1.dao.repositories.contacto;

import com.sigloV1.dao.models.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity,Long> {
    Optional<CargoEntity> findByNombre(String nombre);
}
