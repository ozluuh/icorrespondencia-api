package br.com.icorrespondencia.api.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.icorrespondencia.api.domain.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@JsonView(View.Public.class)
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Room {

    @JsonView(value = { View.Public.class, View.Specific.class })
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 3, nullable = false)
    private Integer number;

    @JsonBackReference
    @JoinColumn
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Block block;
}
