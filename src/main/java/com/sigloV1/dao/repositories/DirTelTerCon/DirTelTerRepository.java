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

    List<DirTelTerEntity> findByTerceroAndDireccionAndUsadaEnContacto(
            TerceroEntity tercero, DireccionEntity direccion, Boolean usadaEnContacto);

    List<DirTelTerEntity> findByTerceroAndUsadaEnContacto(TerceroEntity tercero,Boolean usadaEnContacto);

    @Query(value = "SELECT dt FROM DirTelTerEntity dt WHERE dt.tercero = :tercero AND dt.direccion = :direccion AND dt.usadaEnContacto = true")
    List<DirTelTerEntity> relacionesDirTerComoContacto(
            @Param("tercero") TerceroEntity tercero,
            @Param("direccion") DireccionEntity direccion);

    @Query(value = "SELECT dt FROM DirTelTerEntity dt WHERE dt.tercero = :tercero AND dt.telefono = :telefono AND dt.extension= :extension AND  dt.usadaEnContacto = true")
    List<DirTelTerEntity> relacionesTelExtTerComoContacto(
            @Param("tercero") TerceroEntity tercero,
            @Param("telefono") TelefonoEntity telefono,
            @Param("extension") String ext
    );

    DirTelTerEntity findByTerceroAndTelefonoAndUsadaEnContactoAndExtension(
            TerceroEntity tercero, TelefonoEntity telefono, Boolean usadaEnContacto,String extension);

   DirTelTerEntity findByTerceroAndTelefonoAndDireccionAndUsadaEnContactoAndExtension(
            TerceroEntity tercero, TelefonoEntity telefono, DireccionEntity direccion, Boolean usadaEnContacto,String extension);



}
