package com.sigloV1.dao.repositories.role;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.dao.models.RolTipoTerceroEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolTipoTerceroRep extends JpaRepository<RolTipoTerceroEntity,Long> {
    List<RolTipoTerceroEntity> findByTipoTercero(TipoTerceroEntity tipoTercero);
    Optional<RolTipoTerceroEntity> findByTipoTerceroAndRol(TipoTerceroEntity tipoTercero, RolEntity rol);

    List<RolTipoTerceroEntity> findByRol(RolEntity rol);


    @Query(value = "SELECT rtt FROM RolTipoTerceroEntity AS rtt WHERE LOWER(rtt.rol.nombre) = LOWER('CONTACTO')")
    RolTipoTerceroEntity obtenerRolContacto();
}
