package br.com.icorrespondencia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.icorrespondencia.api.domain.Townhouse;

public interface TownhouseRepository extends JpaRepository<Townhouse, Long> {
}
