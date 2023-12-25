package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.DirTelTerContEntity;
import com.sigloV1.dao.models.DirTelTerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirTelTerConRepository extends JpaRepository<DirTelTerContEntity,Long> {

    List<DirTelTerContEntity> findByDireccionTelefono(DirTelTerEntity direccionTelefono);

    List<DirTelTerContEntity> findByContacto(ContactoEntity contacto);

    Optional<DirTelTerContEntity> findByDireccionTelefonoAndContacto(DirTelTerEntity direccionTelefono, ContactoEntity contacto);

}
