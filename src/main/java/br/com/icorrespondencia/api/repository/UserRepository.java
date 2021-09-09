package br.com.icorrespondencia.api.repository;

import org.springframework.stereotype.Repository;

import br.com.icorrespondencia.api.domain.User;

/**
 * User repository
 *
 * @author Luís Paulino
 * @since 0.2
 * @version 1.0
 */
@Repository
public interface UserRepository extends PersonRepository<User, Long> {
}
