package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.IEmailService;
import com.sigloV1.web.dtos.req.email.EmailReqDTO;
import com.sigloV1.web.dtos.res.email.EmailResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/email")
public class EmailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/{idTerceroRol}")
    public ResponseEntity<EmailResDTO> crearEmailTercero(
            @RequestBody @Valid EmailReqDTO datos, @PathVariable Long idTerceroRol){
        return new ResponseEntity<>(emailService.crearEmailTercero(datos,idTerceroRol), HttpStatus.CREATED);
    }

    @PostMapping("contacto/{idContacto}")
    public ResponseEntity<EmailResDTO> crearEmailContacto(
            @RequestBody @Valid EmailReqDTO datos, @PathVariable Long idContacto
    ){
        return new ResponseEntity<>(emailService.crearEmailContacto(datos,idContacto),HttpStatus.CREATED);
    }

    @GetMapping("/{idTerceroRol}")
    public ResponseEntity<List<EmailResDTO>> obtenerEmailTerceroRol(
            @PathVariable Long idTerceroRol
    ){
        return new ResponseEntity<>(emailService.obtenerEmailTerceroRol(idTerceroRol),HttpStatus.OK);
    }

    @GetMapping("contacto/{idContacto}")
    public ResponseEntity<List<EmailResDTO>> obtenerEmailContacto(
            @PathVariable Long idContacto
    ){
        return new ResponseEntity<>(emailService.obtenerEmailContacto(idContacto),HttpStatus.OK);
    }

    @PatchMapping("activar/{idRelacion}")
    public ResponseEntity<String> activarEmailTerceroRol(
            @PathVariable Long idRelacion
    ){
        emailService.estadoEmailTercero(idRelacion,true);
        return new ResponseEntity<>("El correo para el tercero a sido activado",HttpStatus.OK);
    };

    @PatchMapping("desactivar/{idRelacion}")
    public ResponseEntity<String> desactivarEmailTerceroRol(
            @PathVariable Long idRelacion
    ){
        emailService.estadoEmailTercero(idRelacion,false);
        return new ResponseEntity<>("El correo para el tercero a sido desactivado",HttpStatus.OK);
    };

    @DeleteMapping("/{idRelacion}")
    public ResponseEntity<String> eliminarRelacionEmail(@PathVariable Long idRelacion){
        emailService.eliminarRelacionEmail(idRelacion);
        return new ResponseEntity<>("La relacion a sido eliminada",HttpStatus.NO_CONTENT);
    }
}
