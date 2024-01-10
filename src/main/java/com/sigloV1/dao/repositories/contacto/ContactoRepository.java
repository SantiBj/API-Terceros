package com.sigloV1.dao.repositories.contacto;

import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.models.ContactoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoEntity,Long> {
    Optional<ContactoEntity> findByContactoAndTerceroAndCargo(TerceroEntity contacto, TerceroEntity tercero, CargoEntity cargo);
}
