package com.sigloV1.service.interfaces.contacto;

import com.sigloV1.web.dtos.req.contacto.CargoReqDTO;
import com.sigloV1.web.dtos.res.contacto.CargoResDTO;

import java.util.List;

public interface ICargoService {
    void crearCargo(CargoReqDTO dataCargo);

    List<CargoResDTO> allCargos();

    CargoResDTO editarCargo(CargoReqDTO dataCargo, Long cargoId);

    void eliminarCargo(Long cargoId);
}
