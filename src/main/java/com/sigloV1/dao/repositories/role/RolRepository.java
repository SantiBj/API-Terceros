package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity,Long> {
}
