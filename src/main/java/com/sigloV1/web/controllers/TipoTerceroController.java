package com.sigloV1.web.controllers;

import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.service.interfaces.ITipoTerceroService;
import com.sigloV1.web.dtos.req.TipoTerceroDTORq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tipo-tercero")
public class TipoTerceroController {

    @Autowired
    private ITipoTerceroService tipoTerceroService;

    @GetMapping
    public ResponseEntity<List<TipoTerceroEntity>> listaTipoTerceros() {
        return new ResponseEntity<>(tipoTerceroService.listaTipoTerceros(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value=HttpStatus.CREATED)
    public void crearTipoTercero(@RequestBody @Valid TipoTerceroDTORq newTipoTercero) {
        tipoTerceroService.crearTipoTercero(newTipoTercero);
    }

    @PatchMapping("/estado/{tipoTerceroId}/{estado}")
    @ResponseStatus(HttpStatus.OK)
    public void estadoTipoTercero(@PathVariable Long tipoTerceroId,@PathVariable Boolean estado){
        tipoTerceroService.estadoTipoTercero(tipoTerceroId,estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTerceroEntity> editarTipoTercero(@PathVariable Long id
            , @RequestBody @Valid TipoTerceroDTORq tipoTercero) {
        return new ResponseEntity<>(tipoTerceroService.editarTipoTercero(tipoTercero, id), HttpStatus.OK);
    }
}
