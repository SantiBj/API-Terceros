package com.sigloV1.dao.repositories.relacionesMaM;

import com.sigloV1.dao.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelacionesEmailRepository extends JpaRepository<TerceroRolEmailContEntity,Long> {
    Optional<TerceroRolEmailContEntity> findByTerceroRolAndEmail(TerceroRolTipoTerEntity tercero, EmailEntity email);
    Optional<TerceroRolEmailContEntity> findByContactoAndEmail(ContactoEntity contacto, EmailEntity email);

    List<TerceroRolEmailContEntity> findByTerceroRol(TerceroRolTipoTerEntity tercero);

    List<TerceroRolEmailContEntity> findByContacto(ContactoEntity contacto);
}
