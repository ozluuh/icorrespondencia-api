package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Builder.Default
    @Column(unique = true, nullable = false, updatable = false)
    protected final UUID publicId = UUID.randomUUID();

    @Builder.Default
    @Column(nullable = false, updatable = false)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    protected LocalDateTime excludedAt;

    @Column(nullable = false)
    protected boolean active;
}
