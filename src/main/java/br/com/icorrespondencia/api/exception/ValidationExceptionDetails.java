package br.com.icorrespondencia.api.exception;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final List<FieldValidationDetails> messages;
}
