package com.sigloV1.dao.repositories.contacto;

import com.sigloV1.dao.models.ContactoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoEntity,Long> {


}
