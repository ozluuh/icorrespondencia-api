package br.com.icorrespondencia.api.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Townhouse model to persists data
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@DiscriminatorValue("J")
public class Townhouse extends Person {

    private String site;

    @Column(length = 18, nullable = false)
    private String cnpj;

    @Column
    private String email;

    @Column(length = 11)
    private String phone;

    @OneToMany(mappedBy = "townhouse")
    private List<Block> blocks;
}
