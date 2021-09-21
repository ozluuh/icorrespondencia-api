package br.com.icorrespondencia.api.domain;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false, length = 10)
    private RoleType type;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Townhouse townhouse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Room room;

    @PrePersist
    private void preSave() {
        if (this.type == null) {
            this.type = RoleType.ROLE_USER;
        }
    }
}
