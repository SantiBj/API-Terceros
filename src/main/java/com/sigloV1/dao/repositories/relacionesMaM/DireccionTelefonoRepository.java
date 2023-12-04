package com.sigloV1.dao.repositories.relacionesMaM;

import com.sigloV1.dao.models.DireccionTelefonoEntity;
import com.sigloV1.dao.models.TerceroDireccionEntity;
import com.sigloV1.dao.models.TerceroTelefonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionTelefonoRepository extends JpaRepository<DireccionTelefonoEntity, Long> {
    Optional<DireccionTelefonoEntity> findByDireccionTerAndTelefonoTer(
            TerceroDireccionEntity direccionTer , TerceroTelefonoEntity telefonoTer );

    List<DireccionTelefonoEntity> findByDireccionTerAndContacto(TerceroDireccionEntity direccionTer, Boolean contacto);
}
