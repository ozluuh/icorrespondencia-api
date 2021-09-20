package br.com.icorrespondencia.api.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.icorrespondencia.api.domain.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonView(View.Public.class)
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "users")
@DiscriminatorValue("F")
public class User extends Person {

    private static final String ENTER_A_VALID_MAIL_VALIDATION_MESSAGE = "Enter a valid email";

    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @JsonView(View.Internal.class)
    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    @Column(nullable = false, length = 64)
    private String password;

    @JsonView(View.Internal.class)
    @Email(message = ENTER_A_VALID_MAIL_VALIDATION_MESSAGE, regexp = "^[A-z0-9\\.]+@[A-z0-9]+\\.[A-z]{1,}\\.?[A-z]+$")
    private String email;
}
