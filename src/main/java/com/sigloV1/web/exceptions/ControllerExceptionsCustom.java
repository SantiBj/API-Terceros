package com.sigloV1.web.exceptions;

import com.sigloV1.web.exceptions.TypesExceptions.BadRequestCustom;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  final ResponseEntity<ResponseExceptionCustom> handlerInconsistentData(ConstraintViolationException e
            ,WebRequest w){

        Set<ConstraintViolation<?>> inconsistentData = e.getConstraintViolations();
        Map<String,String> inconsistencies = inconsistentData.stream().collect(
                Collectors.toMap(
                        violation-> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existingValue,newValue)->existingValue + " / " + newValue
                )
        );

        return new ResponseEntity<>(
                ResponseExceptionCustom.builder()
                        .message(inconsistencies.toString())
                        .description(w.getDescription(true))
                        .codeState(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timeStamp(new Date())
                        .build()
          ,HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ResponseExceptionCustom> handlerViolationRestriction(
            DataIntegrityViolationException e,WebRequest w
    ){
        return new ResponseEntity<>(
                ResponseExceptionCustom.builder()
                        .message(e.getMessage())
                        .description(w.getDescription(true))
                        .codeState(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timeStamp(new Date())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
