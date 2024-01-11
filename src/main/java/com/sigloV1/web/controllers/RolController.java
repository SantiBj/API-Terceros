package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.rol.IRolesService;
import com.sigloV1.web.dtos.req.rol.RolAsociacionesReqDTO;
import com.sigloV1.web.dtos.req.rol.RolReqDTO;
import com.sigloV1.web.dtos.res.rol.RolRelacionResDTO;
import com.sigloV1.web.dtos.res.rol.RolResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rol")
public class RolController {

    @Autowired
    private IRolesService rolesService;


    @GetMapping("/tipo-tercero/{tipoTerceroId}")
    public ResponseEntity<List<RolRelacionResDTO>> rolesPorTipoTercero(@PathVariable Long tipoTerceroId){
        return new ResponseEntity<>(rolesService.rolesPorTipoTercero(tipoTerceroId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void guardarRolAsociado(@RequestBody @Valid RolAsociacionesReqDTO rolData){
        rolesService.guardarRolAsociado(rolData);
    }


    @PatchMapping
    public ResponseEntity<RolResDTO> editarRol(@RequestBody @Valid RolReqDTO rolData){
        return new ResponseEntity<>(rolesService.editarRol(rolData),HttpStatus.OK);
    }

    @PatchMapping("/add-tipo-tercero/{rolId}/{tipoTerceroId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addTipoTercero(@PathVariable Long rolId,@PathVariable Long tipoTerceroId){
        rolesService.addTipoTercero(tipoTerceroId,rolId);
    }

}
