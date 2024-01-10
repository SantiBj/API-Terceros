package com.sigloV1.service.impl;

import com.sigloV1.dao.models.CargoEntity;
import com.sigloV1.dao.repositories.contacto.CargoRepository;
import com.sigloV1.service.interfaces.contacto.ICargoService;
import com.sigloV1.service.logica.contacto.LogicCargo;
import com.sigloV1.web.dtos.req.contacto.CargoReqDTO;
import com.sigloV1.web.dtos.res.contacto.CargoResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService implements ICargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private LogicCargo logicCargo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void crearCargo(CargoReqDTO dataCargo) {
        cargoRepository.findByNombre(dataCargo.getNombre()).orElseGet(()->{
            return cargoRepository.save(
                    CargoEntity.builder()
                            .nombre(dataCargo.getNombre())
                            .build()
            );
        });
    }

    @Override
    public List<CargoResDTO> allCargos() {
        return cargoRepository.findAll().stream().map(cargo->
                modelMapper.map(cargo,CargoResDTO.class)
                ).toList();
    }

    @Override
    public CargoResDTO editarCargo(CargoReqDTO dataCargo, Long cargoId) {
        CargoEntity cargo = logicCargo.obtenerCargoOException(cargoId);
        if (!dataCargo.getNombre().equalsIgnoreCase(cargo.getNombre())){
            cargoRepository.findByNombre(dataCargo.getNombre()).ifPresent(value->{
                throw new BadRequestCustom("El nombre que desea asignar ya esta en uso");
            });
        }
        cargo.setNombre(dataCargo.getNombre());
        return modelMapper.map(cargoRepository.save(cargo),CargoResDTO.class);
    }
}
