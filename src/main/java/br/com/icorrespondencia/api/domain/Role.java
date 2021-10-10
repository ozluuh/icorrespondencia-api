package br.com.icorrespondencia.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.icorrespondencia.api.domain.validation.View;

import lombok.Data;

@JsonView(View.Public.class)
@Data
@Entity
public class Role {

    @JsonView(View.Hidden.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RoleType name;

    @OneToOne
    @JoinColumn
    private Townhouse townhouse;

    @OneToOne
    @JoinColumn
    private Room room;

    @PrePersist
    private void onPreSave() {
        if (this.name == null) {
            this.name = RoleType.ROLE_USER;
        }
    }
}
