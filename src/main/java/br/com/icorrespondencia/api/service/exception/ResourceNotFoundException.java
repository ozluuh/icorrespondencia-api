package br.com.icorrespondencia.api.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Generated;

/**
 * When resource not found, throws this exception
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not found");
    }

    @Generated
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
