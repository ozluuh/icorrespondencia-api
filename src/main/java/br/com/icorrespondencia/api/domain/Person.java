package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import br.com.icorrespondencia.api.dto.validation.ValidationGroups;
import br.com.icorrespondencia.api.dto.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type", length = 1)
@DiscriminatorValue("P")
public class Person {

    protected static final String FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE = "Field should not be filled";

    protected static final String FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE = "Field must be filled";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Put.class)
    protected Long id;

    @Column(nullable = false)
    @NotBlank(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    protected String name;

    @Column(unique = true, nullable = false, updatable = false)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Put.class)
    protected final UUID publicId = UUID.randomUUID();

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(nullable = false, updatable = false)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    @JsonView(View.Internal.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    protected LocalDateTime excludedAt;

    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE)
    protected LocalDateTime updatedAt;

    @Column(nullable = false)
    protected boolean active = true;
}
