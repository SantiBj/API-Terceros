package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.contacto.IContactoService;
import com.sigloV1.web.dtos.req.contacto.ContactoReqDTO;
import com.sigloV1.web.dtos.res.contacto.ContactoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacto")
public class ContactoController {

    @Autowired
    private IContactoService contactoService;

    @PostMapping
    public ResponseEntity<ContactoResDTO> crearContacto(@RequestBody ContactoReqDTO data){
        return new ResponseEntity<>(contactoService.crearContacto(data), HttpStatus.CREATED);
    }

    @GetMapping("/{terceroId}")
    public ResponseEntity<List<ContactoResDTO>> contactosTercero(@PathVariable Long terceroId){
        return new ResponseEntity<>(contactoService.contactosTercero(terceroId),HttpStatus.OK);
    }

    @DeleteMapping("/{relacionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void eliminarContacto(Long relacionId){
        contactoService.eliminarContacto(relacionId);
    }

    @PatchMapping("/{relacionId}/{estado}")
    @ResponseStatus(value = HttpStatus.OK)
    public void estadoContacto(Long relacionId,Boolean estado){
        contactoService.estadoContacto(relacionId,estado);
    }

}
