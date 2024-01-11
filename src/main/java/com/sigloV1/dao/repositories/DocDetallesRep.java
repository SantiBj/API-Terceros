package com.sigloV1.dao.repositories;

import com.sigloV1.dao.models.DocDetallesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocDetallesRep extends JpaRepository<DocDetallesEntity,Long> {
}
