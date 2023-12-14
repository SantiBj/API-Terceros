package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.contacto.ICargoService;
import com.sigloV1.web.dtos.req.contacto.CargoReqDTO;
import com.sigloV1.web.dtos.res.contacto.CargoResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cargo")
public class CargoController {

    @Autowired
    private ICargoService cargoService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void crearCargo(@RequestBody @Valid CargoReqDTO dataCargo){
        cargoService.crearCargo(dataCargo);
    }

    @GetMapping
    public ResponseEntity<List<CargoResDTO>> allCargos(){
        return new ResponseEntity<>(cargoService.allCargos(),HttpStatus.OK);
    }

    @PatchMapping("/{cargoId}")
    public ResponseEntity<CargoResDTO> editarCargo(@RequestBody @Valid CargoReqDTO dataCargo,@PathVariable Long cargoId){
        return new ResponseEntity<>(cargoService.editarCargo(dataCargo,cargoId),HttpStatus.OK);
    }
}
