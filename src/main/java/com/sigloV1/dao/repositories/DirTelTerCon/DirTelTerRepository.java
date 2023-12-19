package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.DirTelTerEntity;
import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirTelTerRepository extends JpaRepository<DirTelTerEntity,Long> {

    Optional<DirTelTerEntity> findByTerceroAndDireccionAndUsadaEnContacto(
            TerceroEntity tercero, DireccionEntity direccion, Boolean usadaEnContacto);

    Optional<DirTelTerEntity> findByTerceroAndTelefonoAndUsadaEnContacto(
            TerceroEntity tercero, TelefonoEntity telefono, Boolean usadaEnContacto);

    Optional<DirTelTerEntity> findByTerceroAndTelefonoAndDireccionAndUsadaEnContacto(TerceroEntity tercero,
                                                                                     TelefonoEntity telefono,DireccionEntity direccion, Boolean usadaEnContacto);
}
