package br.com.icorrespondencia.api.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.icorrespondencia.api.exception.BadRequestException;
import br.com.icorrespondencia.api.exception.BadRequestExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException exception){

        return new ResponseEntity<>(
            BadRequestExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception, Check documentation") // error
            .details(exception.getMessage())
            .build(), HttpStatus.BAD_REQUEST
        );
    }
}
