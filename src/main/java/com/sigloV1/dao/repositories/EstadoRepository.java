package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoEntity,Long> {
}
