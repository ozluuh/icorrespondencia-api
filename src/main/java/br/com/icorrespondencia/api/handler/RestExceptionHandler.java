package br.com.icorrespondencia.api.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.icorrespondencia.api.exception.BadRequestException;
import br.com.icorrespondencia.api.exception.BadRequestExceptionDetails;
import br.com.icorrespondencia.api.exception.ValidationExceptionDetails;
import br.com.icorrespondencia.api.util.DateUtil;
import br.com.icorrespondencia.api.util.DetailsExceptionUtil;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException exception) {

        return new ResponseEntity<>(
                BadRequestExceptionDetails
                    .builder()
                    .timestamp(DateUtil.formatDateTimeToSQL(LocalDateTime.now()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title(DetailsExceptionUtil.formatTitle(exception.getClass().getSimpleName()))
                    .details(exception.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        List<String> messages = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                ValidationExceptionDetails
                    .builder()
                    .timestamp(DateUtil.formatDateTimeToSQL(LocalDateTime.now()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title(DetailsExceptionUtil.formatTitle(exception.getClass().getSimpleName()))
                    .details("Validation failed, check messages bellow")
                    .messages(messages)
                    .build(), HttpStatus.BAD_REQUEST);
    }
}
