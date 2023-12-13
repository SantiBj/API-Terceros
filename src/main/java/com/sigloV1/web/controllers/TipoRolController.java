package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.rol.ITipoRolService;
import com.sigloV1.web.dtos.req.rol.TipoRolReqDTO;
import com.sigloV1.web.dtos.res.rol.TipoRolResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tipo-rol")
public class TipoRolController {

    @Autowired
    private ITipoRolService tipoRolService;

    @GetMapping
    public ResponseEntity<List<TipoRolResDTO>> allTipoRoles(){
        return new ResponseEntity<>(tipoRolService.allTipoRoles(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void guardarTipoRol(@RequestBody @Valid TipoRolReqDTO tipoRolData){
        tipoRolService.guardarTipoRol(tipoRolData);
    }

    @PatchMapping("/{tipoRolId}")
    public ResponseEntity<TipoRolResDTO> editarNombreTipoRol(
            @RequestBody @Valid TipoRolReqDTO tipoRolNuevo, @PathVariable Long tipoRolId
    ){
        return new ResponseEntity<>(tipoRolService.editarNombreTipoRol(tipoRolNuevo,tipoRolId),HttpStatus.OK);
    }

    @DeleteMapping("/{tipoRolId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void eliminarTipoRol(@PathVariable Long tipoRolId){
        tipoRolService.eliminarTipoRol(tipoRolId);
    }
}
