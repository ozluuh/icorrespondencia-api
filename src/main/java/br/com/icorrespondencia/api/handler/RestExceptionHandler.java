package br.com.icorrespondencia.api.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.icorrespondencia.api.exception.BadRequestException;
import br.com.icorrespondencia.api.exception.ExceptionDetails;
import br.com.icorrespondencia.api.exception.FieldValidationDetails;
import br.com.icorrespondencia.api.exception.ValidationExceptionDetails;
import br.com.icorrespondencia.api.util.DateUtil;
import br.com.icorrespondencia.api.util.DetailsExceptionUtil;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(
        BadRequestException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldValidationDetails> messages = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> FieldValidationDetails
                        .builder()
                        .field(field.getField())
                        .message(field.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return handleValidationExceptionInternal(ex, messages, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Object cause = Optional.ofNullable(body).orElse(ex.getMessage());

        ExceptionDetails details = ExceptionDetails
            .builder()
            .status(status.value())
            .title(DetailsExceptionUtil.formatTitle(ex.getClass().getSimpleName()))
            .details((String) cause)
            .timestamp(DateUtil.formatDateTimeToSQL(LocalDateTime.now()))
            .build();

        return new ResponseEntity<>(details, headers, status);
    }

    protected ResponseEntity<Object> handleValidationExceptionInternal(
        Exception ex, List<FieldValidationDetails> messages, HttpHeaders headers, HttpStatus status) {

        ValidationExceptionDetails details = ValidationExceptionDetails
            .builder()
            .status(status.value())
            .title(DetailsExceptionUtil.formatTitle(ex.getClass().getSimpleName()))
            .details("Validation failed, check messages bellow")
            .timestamp(DateUtil.formatDateTimeToSQL(LocalDateTime.now()))
            .messages(messages)
            .build();

        return new ResponseEntity<>(details, headers, status);
    }
}
