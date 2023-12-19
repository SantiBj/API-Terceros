package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.DirTelTerContEntity;
import com.sigloV1.dao.models.DirTelTerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirTelTerConRepository extends JpaRepository<DirTelTerContEntity,Long> {

    Optional<DirTelTerContEntity> findByDireccionTelefonoAndContacto(DirTelTerEntity direccionTelefono, ContactoEntity contacto);

}
