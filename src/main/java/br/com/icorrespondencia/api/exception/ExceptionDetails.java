package br.com.icorrespondencia.api.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected String details;
    protected int status;
    protected String timestamp;
}
