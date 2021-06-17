package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_ICR_CONDOMINIO")
@SequenceGenerator(name = "townhouse", allocationSize = 1, sequenceName = "SQ_ICR_CONDOMINIO")
public class Townhouse {

    @Id
    @GeneratedValue(generator = "townhouse", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_condominio", updatable = false)
    private Long id;

    @Column(name = "ds_razao_social", nullable = false)
    private String name;

    @Column(name = "nr_cnpj", unique = true, columnDefinition = "CHAR(18) NOT NULL")
    private String nin;

    @Column(name = "ds_site", length = 100)
    private String site;

    @Column(name = "ds_email", length = 80)
    private String email;

    @Column(name = "dt_cadastro", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "dt_exclusao")
    private LocalDateTime excludedAt;

    @Column(name = "st_ativo", nullable = false)
    private boolean active = true;
}
