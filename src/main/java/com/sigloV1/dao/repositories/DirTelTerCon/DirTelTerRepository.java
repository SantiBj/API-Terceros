package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.DirTelTerEntity;
import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirTelTerRepository extends JpaRepository<DirTelTerEntity,Long> {

    DirTelTerEntity findByTerceroAndDireccionAndUsadaEnContacto(
            TerceroEntity tercero, DireccionEntity direccion, Boolean usadaEnContacto);


    @Query(value = "SELECT dt FROM DirTelTerEntity dt WHERE dt.tercero = :tercero AND dt.direccion = :direccion AND dt.usadaEnContacto = true")
    List<DirTelTerEntity> relacionesDirTerComoContacto(
            @Param("tercero") TerceroEntity tercero,
            @Param("direccion") DireccionEntity direccion);

    DirTelTerEntity findByTerceroAndTelefonoAndUsadaEnContacto(
            TerceroEntity tercero, TelefonoEntity telefono, Boolean usadaEnContacto);

   DirTelTerEntity findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto(
            TerceroEntity tercero, TelefonoEntity telefono, DireccionEntity direccion, Boolean usadaEnContacto);

}
