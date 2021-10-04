package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import br.com.icorrespondencia.api.domain.validation.ValidationGroups;
import br.com.icorrespondencia.api.domain.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type", length = 1)
public abstract class Person {

    protected static final String FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE = "Field should not be filled";

    protected static final String FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE = "Field must be filled";

    @JsonProperty("public_id")
    @JsonAlias({ "publicId", "id", "_id" })
    @JsonView(value = { View.Public.class, View.Specific.class })
    @Id
    @Column(updatable = false, unique = true)
    @Null(message = FIELD_SHOULD_NOT_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE, groups = ValidationGroups.Put.class)
    protected UUID id;

    @Column(nullable = false)
    @NotBlank(message = FIELD_MUST_BE_FILLED_VALIDATION_MESSAGE)
    protected String name;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @JsonView(View.Specific.class)
    @JsonSerialize(using = ToStringSerializer.class)
    protected LocalDateTime excludedAt;

    protected LocalDateTime updatedAt;

    @Column(nullable = false)
    protected boolean active = true;

    @PrePersist
    private void onPreSave() {
        log.debug("PrePersist called");
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
