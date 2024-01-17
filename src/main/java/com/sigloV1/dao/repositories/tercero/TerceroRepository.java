package com.sigloV1.dao.repositories.tercero;

import com.sigloV1.dao.models.DocDetallesEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//evaluar por el nombre el tipo de tercero, en empresa validar que sea JURIDICA-> empresa y NATURAL -> persona normal

@Repository
public interface TerceroRepository extends JpaRepository<TerceroEntity,Long> {
    @Query(value = "SELECT t FROM TerceroEntity AS t WHERE t.pais.id= :paisId" +
            " AND t.identificacion= :identificacion AND t.docDetalles.id= :docDetallesId" +
            " AND t.nombreComercial= :nombreComercial AND t.razonSocial= :razonSocial" +
            " AND (t.terceroPadre.id = :terceroPadre OR t.terceroPadre IS NULL) " +
            " AND t.tipoTercero.id = :tipoTerceroId")
    Optional<TerceroEntity> empresaExistente (
            @Param("paisId") Long paisId,
            @Param("identificacion") String identificacion,
            @Param("docDetallesId") Long docDetallesId,
            @Param("nombreComercial") String nombreComercial,
            @Param("razonSocial") String razonSocial,
            @Param("terceroPadre") Long terceroPadre,
            @Param("tipoTerceroId") Long tipoTerceroId
            );


    @Query(value = "SELECT t FROM TerceroEntity t WHERE t.pais.id= :paisId" +
            " AND t.identificacion= :identificacion AND t.docDetalles.id= :docDetallesId" +
            " AND t.nombre= :nombre AND LOWER(t.tipoTercero.nombre) = LOWER('NATURAL')")
    Optional<TerceroEntity> personaExistente(
            @Param("paisId") Long paisId,
            @Param("identificacion") String identificacion,
            @Param("docDetallesId") Long docDetallesId,
            @Param("nombre") String nombre
            );
}
