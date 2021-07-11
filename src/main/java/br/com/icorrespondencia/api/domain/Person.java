package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Person {

    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ds_name", nullable = false)
    private String name;

    @Column(name = "nb_nin", length = 18, unique = true, nullable = false)
    private String nin;

    @Column(name = "ds_email", unique = true)
    private String email;

    @Builder.Default
    @Column(name = "dt_created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "dt_excluded_at")
    private LocalDateTime excludedAt;

    @Builder.Default
    @Column(name = "st_active", nullable = false)
    private boolean active = true;
}
