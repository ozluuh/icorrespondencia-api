package br.com.icorrespondencia.api.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @JsonView(View.Internal.class)
    @Column(nullable = false, length = 64)
    private String password;

    @JsonView(View.Internal.class)
    private String email;
}
