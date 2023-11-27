package com.sigloV1.web.exceptions.TypesExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCustom extends RuntimeException {
    public BadRequestCustom(String message){
        super(message);
    }
}
