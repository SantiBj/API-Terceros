package com.sigloV1.service.logica.contacto;


import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.repositories.contacto.CargoRepository;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicCargo {

    @Autowired
    private CargoRepository cargoRepository;

    public CargoEntity obtenerCargoOException(Long cargoId){
        return cargoRepository.findById(cargoId)
                .orElseThrow(()->new BadRequestCustom("El cargo no existe"));
    }
}
