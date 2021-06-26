package br.com.icorrespondencia.api.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldValidationDetails {
    private final String field;
    private final String message;
}
