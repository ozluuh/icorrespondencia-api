package br.com.icorrespondencia.api.exception;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Utility class that extends {@link ExceptionDetails} to custom validation
 * messages
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final List<FieldValidationDetails> messages;
}
