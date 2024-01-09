package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.web.dtos.req.DirTelTerCon.DatosDeContactoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/datos-contacto")
public class DirTelTerController {

    @Autowired
    private IAllRelacionesService allRelacionesService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void crearDatosDeContacto(@RequestBody @Valid DatosDeContactoDTO data){
        allRelacionesService.crearDatosDeContacto(data);
    }

}
