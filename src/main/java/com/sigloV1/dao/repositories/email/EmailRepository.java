package com.sigloV1.dao.repositories.email;


import com.sigloV1.dao.models.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
    Optional<EmailEntity> findByEmailIgnoreCase(String email);
}
