package com.sigloV1.web.controllers;

import com.sigloV1.service.impl.TerceroService;
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
    private TerceroService terceroService;

    @PostMapping
    public ResponseEntity<Long> crearTercero(@RequestBody @Valid TerceroReqDTO data){
        return new ResponseEntity<>(terceroService.crearTercero(data), HttpStatus.CREATED);
    }
}
