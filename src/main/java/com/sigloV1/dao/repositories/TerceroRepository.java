package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerceroRepository extends JpaRepository<TerceroEntity,Long> {
}
