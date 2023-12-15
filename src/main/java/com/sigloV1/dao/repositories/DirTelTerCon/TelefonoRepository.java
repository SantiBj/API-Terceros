package com.sigloV1.dao.repositories.DirTelTerCon;

import com.sigloV1.dao.models.TelefonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<TelefonoEntity,Long> {
    Optional<TelefonoEntity> findByNumero(String numero);
}
