package br.com.icorrespondencia.api.dto;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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

    protected static final String ENTER_A_VALID_MAIL_VALIDATION_MESSAGE = "Enter a valid email";

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

    @Email(message = ENTER_A_VALID_MAIL_VALIDATION_MESSAGE, regexp = "^[A-z0-9]+@[A-z0-9]+\\.[A-z]{2,}\\.?[A-z]+$")
    protected String email;

    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected String createdAt;

    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected String excludedAt;

    protected boolean active;
}
