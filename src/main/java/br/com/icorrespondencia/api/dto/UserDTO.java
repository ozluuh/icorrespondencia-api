package br.com.icorrespondencia.api.dto;

import javax.validation.constraints.NotEmpty;

import br.com.icorrespondencia.api.exception.ValidationGroups;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Townhouse Transfer Object between layers
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDTO extends PersonDTO {

    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE,
            groups = { ValidationGroups.Post.class, ValidationGroups.Put.class })
    private String username;

    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE,
            groups = { ValidationGroups.Post.class, ValidationGroups.Put.class })
    private String password;
}