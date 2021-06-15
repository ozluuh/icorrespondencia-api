package br.com.icorrespondencia.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "townhouse", allocationSize = 1, sequenceName = "SQ_ICR_CONDOMINIO")
public class Townhouse {

    @Id
    @GeneratedValue(generator = "townhouse", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

}
