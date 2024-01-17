package com.sigloV1.web.controllers;


import com.sigloV1.service.interfaces.ITerceroService;
import com.sigloV1.web.dtos.req.tercero.TerceroContactoDTO;
import com.sigloV1.web.dtos.req.tercero.TerceroReqDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tercero")
public class TerceroController {

    @Autowired
    private ITerceroService terceroService;

    @PostMapping
    public ResponseEntity<Long> crearTercero(@RequestBody @Valid TerceroReqDTO data){
        return new ResponseEntity<>(terceroService.crearTercero(data), HttpStatus.CREATED);
    }

    @PostMapping("/contacto")
    public ResponseEntity<Long> crearContacto(@RequestBody @Valid TerceroContactoDTO data){
        return new ResponseEntity<>(terceroService.crearContacto(data),HttpStatus.CREATED);
    }
}
