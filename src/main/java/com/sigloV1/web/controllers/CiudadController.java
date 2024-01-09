package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.ICiudadService;
import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReq;
import com.sigloV1.web.dtos.req.ciudad.CiudadDTOReqDir;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTORes;
import com.sigloV1.web.dtos.res.ciudad.CiudadDTOResDir;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("api/ciudad")
public class CiudadController {

    @Autowired
    private ICiudadService ciudadService;

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDTORes> obtenerCiudad(@PathVariable Long id) {
        return new ResponseEntity<>(ciudadService.obtenerCiudad(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CiudadDTORes> crearCiudad(@RequestBody @Valid CiudadDTOReq ciudad) {
        return new ResponseEntity<>(ciudadService.crearCiudad(ciudad), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCiudad(@PathVariable Long id){
        ciudadService.eliminarCiudad(id);
        return new ResponseEntity<>("La ciudad a sido eliminado con exito.",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CiudadDTORes> editarCiudad(@RequestBody @Valid CiudadDTOReq newCiudad,@PathVariable Long id){
        return new ResponseEntity<>(ciudadService.editarCiudad(newCiudad,id),HttpStatus.OK);
    }

}
