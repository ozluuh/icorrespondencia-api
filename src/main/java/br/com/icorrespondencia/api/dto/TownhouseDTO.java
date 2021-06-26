package br.com.icorrespondencia.api.dto;

import javax.validation.constraints.NotEmpty;

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

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty(message = "Field must be filled")
    private String name;

    @NotEmpty(message = "Field must be filled")
    @EqualsAndHashCode.Include
    private String cnpj;

    private String email;

    private String site;

    private String createdAt;

    private String excludedAt;

    private boolean active;
}
