package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.icorrespondencia.api.domain.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Townhouse model to persists data
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
@JsonInclude(Include.NON_NULL)
@JsonView(View.Public.class)
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@DiscriminatorValue("J")
public class Townhouse extends Person {

    private static final String ENTER_A_VALID_MAIL_VALIDATION_MESSAGE = "Enter a valid email";

    private String site;

    @NotEmpty(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    @Column(length = 18, nullable = false)
    private String cnpj;

    @Email(message = ENTER_A_VALID_MAIL_VALIDATION_MESSAGE, regexp = "^[A-z0-9\\.]+@[A-z0-9]+\\.[A-z]{1,}\\.?[A-z]+$")
    private String email;

    @Column(length = 11)
    private String phone;

    @JsonView(View.Private.class)
    @JsonManagedReference
    @OneToMany(mappedBy = "townhouse", cascade = CascadeType.ALL)
    private List<Block> blocks;
}
