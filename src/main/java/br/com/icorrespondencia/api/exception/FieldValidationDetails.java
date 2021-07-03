package br.com.icorrespondencia.api.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Utility class to custom fields constraints
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Builder
@Getter
public class FieldValidationDetails {
    private final String field;
    private final String message;
}
