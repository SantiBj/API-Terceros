package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.dao.models.TipoRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity> findByNombreIgnoreCase(String nombre);

    List<RolEntity> findByTipoRol(TipoRolEntity tipoRol);
}
