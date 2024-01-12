package com.sigloV1.web.controllers;

import com.sigloV1.dao.models.RolEntity;
import com.sigloV1.dao.models.TerceroRolTipoTerEntity;
import com.sigloV1.service.interfaces.ITerceroRol;
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

    @Autowired
    private ITerceroRol terceroRolService;


    @GetMapping("/tipo-tercero/{tipoTerceroId}")
    public ResponseEntity<List<RolRelacionResDTO>> rolesPorTipoTercero(@PathVariable Long tipoTerceroId){
        return new ResponseEntity<>(rolesService.rolesPorTipoTercero(tipoTerceroId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void crearRolAsociado(@RequestBody @Valid RolAsociacionesReqDTO rolData){
        rolesService.crearRolAsociado(rolData);
    }

    @PatchMapping("/add-tipo-tercero/{rolId}/{tipoTerceroId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addTipoTercero(@PathVariable Long rolId,@PathVariable Long tipoTerceroId){
        rolesService.addTipoTercero(tipoTerceroId,rolId);
    }

    @PatchMapping
    public ResponseEntity<RolEntity> editarRol(@RequestBody @Valid RolReqDTO rolData){
        return new ResponseEntity<>(rolesService.editarRol(rolData),HttpStatus.OK);
    }

    @PostMapping("relacion-tercero-rol/{terceroId}/{rolFullId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void relacionarTerceroRol(
            @PathVariable Long terceroId,@PathVariable Long rolFullId
    ){
        terceroRolService.relacionarTerceroRol(terceroId,rolFullId);
    }

    @PatchMapping("estado/{rolFullId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoTerceroRol(@PathVariable Long rolFullId,@PathVariable Boolean estado){
        terceroRolService.estadoTerceroRol(rolFullId,estado);
    }
}
