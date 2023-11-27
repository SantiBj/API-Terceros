package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<CiudadEntity,Long> {
}
