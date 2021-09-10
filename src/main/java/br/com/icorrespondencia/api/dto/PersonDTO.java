package br.com.icorrespondencia.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import br.com.icorrespondencia.api.exception.ValidationGroups;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonDTO {

    protected static final String FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE = "Field should not be filled";

    protected static final String FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE = "Field must be filled";

    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Put.class)
    protected Long id;

    @NotBlank(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    protected String name;

    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Put.class)
    protected UUID publicId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected LocalDateTime createdAt;

    @JsonSerialize(using = ToStringSerializer.class)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected LocalDateTime excludedAt;

    protected boolean active;
}
