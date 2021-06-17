package br.com.icorrespondencia.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TownhouseDTO {

    private Long id;

    private String name;

    @JsonProperty("cnpj")
    private String nin;

    private String email;

    private String site;

    private String createdAt;

    @JsonIgnore
    private LocalDateTime excludedAt;

    @JsonIgnore
    private boolean active;
}
