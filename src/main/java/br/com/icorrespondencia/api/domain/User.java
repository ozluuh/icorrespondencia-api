package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "id_public", updatable = false, nullable = false)
    private final UUID publicId = UUID.randomUUID();

    @JoinColumn(name = "id_person", nullable = false)
    private Person person;

    @Column(name = "ds_username", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "ds_password", nullable = false, length = 128)
    private String password;

    @Builder.Default
    @Column(name = "dt_created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "dt_excluded_at")
    private LocalDateTime excludedAt;

    @Builder.Default
    @Column(name = "st_active", nullable = false)
    private boolean active = true;
}
