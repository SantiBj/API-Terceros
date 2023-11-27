package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.ITipoTerceroService;
import com.sigloV1.web.dtos.req.TipoTerceroDTORq;
import com.sigloV1.web.dtos.res.TipoTerceroDTORs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.List;

@RestController
@RequestMapping("api/tipo-tercero")
public class TipoTerceroController {

    @Autowired
    private ITipoTerceroService tipoTerceroService;

    @GetMapping
    public ResponseEntity<List<TipoTerceroDTORs>> listaTipoTerceros() {
        return new ResponseEntity<>(tipoTerceroService.listaTipoTerceros(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TipoTerceroDTORs> crearTipoTercero(@RequestBody @Valid TipoTerceroDTORq newTipoTercero) {
        return new ResponseEntity<>(tipoTerceroService.crearTipoTercero(newTipoTercero), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarTipoTercero(@PathVariable Long id) {
        tipoTerceroService.desactivarTipoTercero(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTerceroDTORs> editarTipoTercero(@PathVariable Long id
            , @RequestBody @Valid TipoTerceroDTORq tipoTercero) {
        return new ResponseEntity<>(tipoTerceroService.editarTipoTercero(tipoTercero, id), HttpStatus.OK);
    }

    @GetMapping("/activar/{id}")
    public ResponseEntity<TipoTerceroDTORs> activarTipoTercero(@PathVariable Long id) {
        return new ResponseEntity<>(tipoTerceroService.activarTipoTercero(id), HttpStatus.OK);
    }


}
