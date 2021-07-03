package br.com.icorrespondencia.api.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Utility class to custom exceptions
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected String details;
    protected int status;
    protected String timestamp;
}
