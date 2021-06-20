package br.com.icorrespondencia.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TownhouseDTO {

    private Long id;

    @NotEmpty(message = "Field name must be filled")
    private String name;

    @JsonProperty("cnpj")
    @NotEmpty(message="Field cnpj must be filled")
    private String nin;

    private String email;

    private String site;

    private String createdAt;

    // @JsonIgnore
    private LocalDateTime excludedAt;

    // @JsonIgnore
    private boolean active;
}
