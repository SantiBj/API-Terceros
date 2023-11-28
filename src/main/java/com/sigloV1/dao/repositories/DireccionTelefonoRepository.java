package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.DireccionTelefonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionTelefonoRepository extends JpaRepository<DireccionTelefonoEntity,Long> {
}