package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.direccionesTelefonos.IAllRelacionesService;
import com.sigloV1.web.dtos.req.DirTelTerCon.DatosDeContactoDTO;
import com.sigloV1.web.dtos.res.dirTelTerCon.DireccionesAndTelefonosDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/datos-contacto")
public class DirTelTerController {

    @Autowired
    private IAllRelacionesService allRelacionesService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void crearDatosDeContacto(@RequestBody @Valid DatosDeContactoDTO data){
        //los datos se guardaran en el contacto no en el dueño del contacto
        allRelacionesService.crearDatosDeContacto(data);
    }

    @GetMapping("/{terceroId}")
    public ResponseEntity<DireccionesAndTelefonosDTO> obtenerDatosTercero(@PathVariable Long terceroId){
        return new ResponseEntity<>(allRelacionesService.direccionTelefonosTercero(terceroId),HttpStatus.OK);
    }

    @GetMapping("/contacto/{contactoId}")
    public ResponseEntity<DireccionesAndTelefonosDTO> obtenerDatosContacto(@PathVariable Long contactoId){
        return new ResponseEntity<>(allRelacionesService.direccionTelefonosTercero(contactoId),HttpStatus.OK);
    }

    @PatchMapping("/estado-direccion/{relacionId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoDireccion(@PathVariable Long relacionId,@PathVariable Boolean estado){
        //0 -> false -> disable
        allRelacionesService.estadoDireccionTercero(relacionId,estado);
    }

    @PatchMapping("/estado-direccion-contacto/{relacionId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoDireccionContacto(@PathVariable Long relacionId,@PathVariable Boolean estado){
        allRelacionesService.estadoDireccionContacto(relacionId,estado);
    }

    @PatchMapping("/estado-telefono/{relacionId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoTelefonoTercero(@PathVariable Long relacionId,@PathVariable Boolean estado){
        allRelacionesService.estadoTelefonoTercero(relacionId,estado);
    }

    @PatchMapping("/estado-telefono-contacto/{relacionId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoTelefonoContacto(@PathVariable Long relacionId,@PathVariable Boolean estado){
        allRelacionesService.estadoTelefonoContacto(relacionId,estado);
    }

    @DeleteMapping("/{relacionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void eliminarRelacionDatosTercero(@PathVariable Long relacionId){
        allRelacionesService.eliminarRelacionTercero(relacionId);
    }

    @DeleteMapping("/contacto/{relacionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void eliminarRelacionDatosContacto(@PathVariable Long relacionId){
        allRelacionesService.eliminarRelacionContacto(relacionId);
    }
}
