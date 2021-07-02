package br.com.icorrespondencia.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import br.com.icorrespondencia.api.exception.ValidationGroups;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TownhouseDTO {

    private static final String FIELD_MUST_BE_NULL = "Field must be null";

    private static final String FIELD_MUST_BE_FILLED = "Field must be filled";

    @Null(message = FIELD_MUST_BE_NULL, groups = ValidationGroups.Post.class)
    @NotNull(message = FIELD_MUST_BE_FILLED, groups = ValidationGroups.Put.class)
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty(message = FIELD_MUST_BE_FILLED)
    private String name;

    @NotEmpty(message = FIELD_MUST_BE_FILLED)
    @EqualsAndHashCode.Include
    private String cnpj;

    private String email;

    private String site;

    @Null(message = FIELD_MUST_BE_NULL, groups = ValidationGroups.Post.class)
    private String createdAt;

    @Null(message = FIELD_MUST_BE_NULL, groups = ValidationGroups.Post.class)
    private String excludedAt;

    private boolean active;
}
