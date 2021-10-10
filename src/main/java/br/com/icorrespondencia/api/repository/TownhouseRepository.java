package br.com.icorrespondencia.api.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.icorrespondencia.api.domain.Townhouse;

/**
 * Layer to access data objects in repository
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
@Repository
public interface TownhouseRepository extends PersonRepository<Townhouse, UUID> {
}
