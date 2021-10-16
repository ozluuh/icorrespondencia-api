package br.com.icorrespondencia.api.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.icorrespondencia.api.controller.exception.KeysNotPresentException;
import br.com.icorrespondencia.api.exception.ExceptionDetails;
import br.com.icorrespondencia.api.exception.FieldValidationDetails;
import br.com.icorrespondencia.api.exception.ValidationExceptionDetails;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import br.com.icorrespondencia.api.util.DateUtil;
import br.com.icorrespondencia.api.util.DetailsExceptionUtil;

/**
 * Handler that intercepts {@literal exceptions} and performs treatment before
 * returns client response
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleViolationKey(ConstraintViolationException ex, WebRequest request) {
        String message = ex.getSQLException().getMessage();
        String detailsMessage = message.substring(message.indexOf("Detail:"));
        String filteredMessage = detailsMessage.replaceAll("(.*|\\=)\\s?\\((\\w+)\\)", "$2");
        String finalMessage = filteredMessage.replaceAll("^(\\w+) already exists\\.$", "Usuário $1 já existe! Deseja recuperar a senha?");

        return handleExceptionInternal(ex, finalMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Customize the response for ResourceNotFoundException
     * <p>
     * This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(value = { ResourceNotFoundException.class, KeysNotPresentException.class })
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Customize the response for MethodArgumentNotValidException
     * <p>
     * This method delegates to {@link #handleValidationExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
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

    /**
     * Bridge method to customize the response body of all exception types
     *
     * @param ex      the exception
     * @param body    the body for the response
     * @param headers the headers for the response
     * @param status  the response status
     * @param request the current request
     */
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

    /**
     * Bridge method to customize the response body of validation exception types
     *
     * @param ex       the exception
     * @param messages the messages for the response
     * @param headers  the headers for the response
     * @param status   the response status
     * @param request  the current request
     */
    protected ResponseEntity<Object> handleValidationExceptionInternal(
        Exception ex, List<FieldValidationDetails> messages, HttpHeaders headers, HttpStatus status) {

        ValidationExceptionDetails details = ValidationExceptionDetails
            .builder()
            .status(status.value())
            .title(DetailsExceptionUtil.formatTitle(ex.getClass().getSimpleName()))
            .details("Payload constraint, check messages below")
            .timestamp(DateUtil.formatDateTimeToSQL(LocalDateTime.now()))
            .messages(messages)
            .build();

        return new ResponseEntity<>(details, headers, status);
    }
}
