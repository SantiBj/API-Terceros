package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.IDireccionService;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/direccion")
public class DireccionController {

    @Autowired
    private IDireccionService direccionService;

    @PostMapping("/{tercero}")
    public ResponseEntity<String> crearDireccionFusionTercero(@RequestBody @Valid DireccionReqDTO direccion, @PathVariable Long tercero){
        direccionService.crearDireccionSegunCorresponda(direccion,tercero);
        return new ResponseEntity<>("La direccion a sido creada",HttpStatus.CREATED);

    }

    @PostMapping("/telefonos/{tercero}")
    public ResponseEntity<String> crearDireccionTelefonosFusionTercero(@RequestBody @Valid DireccionTelefonosReqDTO direccion, @PathVariable Long tercero){
        direccionService.crearDireccionSegunCorresponda(direccion,tercero);
        return new ResponseEntity<>("La direccion a sido creada con sus respectivos telefonos",HttpStatus.CREATED);
    }
}
