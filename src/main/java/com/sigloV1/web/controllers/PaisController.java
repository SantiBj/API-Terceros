package com.sigloV1.web.controllers;

import com.sigloV1.service.interfaces.IPaisService;
import com.sigloV1.web.dtos.req.pais.PaisDTOReq;
import com.sigloV1.web.dtos.req.pais.PaisDTOReqBi;
import com.sigloV1.web.dtos.res.pais.PaisDTORes;
import com.sigloV1.web.dtos.res.pais.PaisDTOResBi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pais")
public class PaisController {

    @Autowired
    private IPaisService paisService;

    @GetMapping
    public ResponseEntity<List<PaisDTORes>> obtenerPaises(){
        return new ResponseEntity<>(paisService.obtenerPaises(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTOResBi> obtenerPais(@PathVariable Long id){
        return new ResponseEntity<>(paisService.obtenerPais(id),HttpStatus.OK);
    }

    @PostMapping("/create-bi")
    public ResponseEntity<PaisDTOResBi> crearPaisBi(@RequestBody @Valid PaisDTOReqBi pais){
        return new ResponseEntity<>(paisService.crearPaisBi(pais),HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<PaisDTORes> crearPais(@RequestBody @Valid PaisDTOReq pais){
        return new ResponseEntity<>(paisService.crearPais(pais),HttpStatus.CREATED);
    }

    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<PaisDTORes> desactivarPais(@PathVariable Long id){
        return new ResponseEntity<>(paisService.desactivarPais(id),HttpStatus.OK);
    }

    @GetMapping("/desactivados")
    public ResponseEntity<List<PaisDTORes>> paisesDesactivados(){
        return new ResponseEntity<>(paisService.paisesDesactivados(),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PaisDTOResBi> editarPais(@RequestBody @Valid PaisDTOReq pais,@PathVariable Long id){
        return new ResponseEntity<>(paisService.editarPais(pais,id),HttpStatus.OK);
    }

}
