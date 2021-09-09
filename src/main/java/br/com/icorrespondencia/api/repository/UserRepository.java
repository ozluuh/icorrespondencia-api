package br.com.icorrespondencia.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.icorrespondencia.api.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getOneByIdAndExcludedAtIsNull(Long id);

    List<User> findAllByExcludedAtIsNull();
}
