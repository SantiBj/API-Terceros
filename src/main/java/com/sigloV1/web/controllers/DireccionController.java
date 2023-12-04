package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.IDireccionService;
import com.sigloV1.web.dtos.req.direccion.DireccionReqDTO;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import com.sigloV1.web.dtos.res.direccion.DireccionTelefonosResDTO;
import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/direccion")
public class DireccionController {

    @Autowired
    private IDireccionService direccionService;

    @GetMapping("/{idTercero}")
    public ResponseEntity<List<DireccionTelefonosResDTO>> obtenerDireccionesPorTercero(@PathVariable Long idTercero){
        return new ResponseEntity<>(direccionService.obtenerDireccionesPorTercero(idTercero),HttpStatus.OK);
    }

    @PostMapping("/{tercero}")
    public ResponseEntity<String> crearDireccionFusionTercero(@RequestBody @Valid DireccionReqDTO direccion, @PathVariable Long tercero) {
        direccionService.crearDireccionSegunCorresponda(direccion, tercero);
        return new ResponseEntity<>("La direccion a sido creada", HttpStatus.CREATED);

    }

    @PostMapping("/telefonos/{tercero}")
    public ResponseEntity<String> crearDireccionTelefonosFusionTercero(@RequestBody @Valid DireccionTelefonosReqDTO direccion, @PathVariable Long tercero) {
        direccionService.crearDireccionSegunCorresponda(direccion, tercero);
        return new ResponseEntity<>("La direccion a sido creada con sus respectivos telefonos", HttpStatus.CREATED);
    }

    @PatchMapping("/desactivar/{id}/{idTercero}")
    public ResponseEntity<String> desactivarDireccionTercero(@PathVariable Long id, @PathVariable Long idTercero) {
        direccionService.desactivarDireccionTercero(id, idTercero);
        return new ResponseEntity<>("La direccion del tercero a sido desactivada con sus telefonos.", HttpStatus.OK);
    }

    @DeleteMapping("/{idDireccion}/{idTercero}")
    public ResponseEntity<String> eliminarDireccion(@PathVariable Long idDireccion
            , @PathVariable Long idTercero) {
        direccionService.eliminarDireccion(idDireccion,idTercero);
        return new ResponseEntity<>("Las relaciones entre la direccion han sido eliminadas con exito."
                , HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{idDireccion}")
    public ResponseEntity<String> editarDireccion(@RequestBody @Valid DireccionReqDTO datos,@PathVariable Long idDireccion){
        direccionService.editarDireccion(datos,idDireccion);
        return new ResponseEntity<>("La direccion fue actualizada con exito.",HttpStatus.OK);
    }


}
