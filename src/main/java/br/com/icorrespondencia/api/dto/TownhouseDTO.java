package br.com.icorrespondencia.api.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.icorrespondencia.api.domain.Block;
import br.com.icorrespondencia.api.dto.validation.View;

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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonView(View.Public.class)
public class TownhouseDTO extends PersonDTO {

    private static final String ENTER_A_VALID_MAIL_VALIDATION_MESSAGE = "Enter a valid email";

    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    private String cnpj;

    private String site;

    @Email(message = ENTER_A_VALID_MAIL_VALIDATION_MESSAGE, regexp = "^[A-z0-9\\.]+@[A-z0-9]+\\.[A-z]{1,}\\.?[A-z]+$")
    private String email;

    private String phone;

    private List<Block> blocks;
}
