package com.sigloV1.dao.repositories.tercero;

import com.sigloV1.dao.models.DocDetallesEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerceroRepository extends JpaRepository<TerceroEntity,Long> {
    @Query(value = "SELECT t FROM TerceroEntity t WHERE t.identificacion= :identificacion AND t.docDetalles.id= :docDetallesId AND t.nombreComercial= :nombreComercial AND t.razonSocial= :razonSocial")
    Optional<TerceroEntity> terceroExistente (
            @Param("identificacion") String identificacion,
            @Param("docDetallesId") Long docDetallesId,
            @Param("nombreComercial") String nombreComercial,
            @Param("razonSocial") String razonSocial
            );
}
