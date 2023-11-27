package com.sigloV1.web.exceptions;

import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionsCustom {

    @ExceptionHandler(BadRequestCustom.class)
    public final ResponseEntity<ResponseExceptionCustom> handlerBadRequestException(BadRequestCustom e
            , WebRequest w){
        return new ResponseEntity<>(
                ResponseExceptionCustom.builder()
                        .message(e.getMessage())
                        .description(w.getDescription(true))
                        .codeState(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timeStamp(new Date())
                        .build()
        , HttpStatus.BAD_REQUEST);
    }
}
