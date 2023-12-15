package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.DirTelTerContEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirTelTerConRepository extends JpaRepository<DirTelTerContEntity,Long> {
}
