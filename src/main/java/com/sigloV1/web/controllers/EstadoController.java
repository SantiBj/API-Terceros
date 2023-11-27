package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.IEstadoService;
import com.sigloV1.web.dtos.req.estado.EstadoDTOReqBiPais;
import com.sigloV1.web.dtos.req.estado.EstadoDTOReqPais;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResBiPais;
import com.sigloV1.web.dtos.res.estado.EstadoDTOResPais;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/estado")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTOResBiPais> obtenerEstado(@PathVariable Long id){
        return new ResponseEntity<>(estadoService.obtenerEstado(id), HttpStatus.OK);
    }

    @PostMapping("/create-ciudades")
    public ResponseEntity<EstadoDTOResBiPais> crearEstadoBi(@RequestBody @Valid EstadoDTOReqBiPais estado){
        return new ResponseEntity<>(estadoService.crearEstadoBi(estado),HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<EstadoDTOResPais> crearEstado(@RequestBody @Valid EstadoDTOReqPais estado){
        return new ResponseEntity<>(estadoService.crearEstado(estado),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstado(@PathVariable Long id){
        estadoService.eliminarEstado(id);
        return new ResponseEntity<>("Estado eliminado con exito",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTOResPais> editarEstado(@RequestBody @Valid EstadoDTOReqPais estado,@PathVariable Long id){
        return new ResponseEntity<>(estadoService.editarEstado(estado,id),HttpStatus.OK);
    }
}
