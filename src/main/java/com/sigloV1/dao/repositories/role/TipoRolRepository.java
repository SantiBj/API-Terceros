package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.TipoRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRolRepository extends JpaRepository<TipoRolEntity,Long> {
}
