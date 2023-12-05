package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.ITelefonoService;
import com.sigloV1.web.dtos.req.telefono.TelefonoReqDTO;
import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/telefono")
public class TelefonoController {

    @Autowired
    private ITelefonoService telefonoService;


    @GetMapping("/{idTercero}")
    public ResponseEntity<List<TelefonoResDTO>> obtenerTelefonosTercero(@PathVariable Long idTercero) {
        return new ResponseEntity<>(telefonoService.obtenerTelefonosTercero(idTercero), HttpStatus.OK);
    }

    @PostMapping("/{idTercero}")
    public ResponseEntity<List<TelefonoResDTO>> crearTelefonoTercero(@RequestBody @Valid List<TelefonoReqDTO> telefonos
            , @PathVariable Long idTercero) {
        return new ResponseEntity<>(telefonoService
                .crearTelefonosTercero(telefonos, idTercero),
                HttpStatus.CREATED);
    }

    @PutMapping("/{idTelefono}/{idTercero}")
    public ResponseEntity<TelefonoResDTO> editarTelefono(@PathVariable Long idTelefono
            , @PathVariable Long idTercero, @RequestBody @Valid TelefonoReqDTO datos) {
        return new ResponseEntity<>(telefonoService
                .editarTelefono(idTelefono, idTercero, datos)
                , HttpStatus.OK);
    }

    @PatchMapping("/desactivar/{idTelefono}/{idTercero}")
    public ResponseEntity<Boolean> desactivarTelefonoTercero(
            @PathVariable Long idTelefono, @PathVariable Long idTercero
    ) {
        return new ResponseEntity<>(telefonoService
                .estadoTelefonoTercero(idTelefono, idTercero, false), HttpStatus.OK);
    }

    @PatchMapping("/activar/{idTelefono}/{idTercero}")
    public ResponseEntity<Boolean> activarTelefonoTercero(
            @PathVariable Long idTelefono, @PathVariable Long idTercero
    ) {
        return new ResponseEntity<>(telefonoService
                .estadoTelefonoTercero(idTelefono, idTercero, true), HttpStatus.OK);
    }

    @DeleteMapping("/{idTelefono}/{idTercero}/{idDireccion}")
    public ResponseEntity<String> eliminarTelefonoTercero(
            @PathVariable Long idTelefono, @PathVariable Long idTercero, @PathVariable Long idDireccion
    ) {
        telefonoService.eliminarTelefonoTercero(idTelefono, idTercero, idDireccion);
        return new ResponseEntity<>("Las relaciones del telefono han sido eliminadas."
                , HttpStatus.OK);
    }
}
