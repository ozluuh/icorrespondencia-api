package br.com.icorrespondencia.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.icorrespondencia.api.domain.Delivery;
import br.com.icorrespondencia.api.domain.User;

/**
 * User repository
 *
 * @author Luís Paulino
 * @since 0.2
 * @version 1.0
 */
@Repository
public interface UserRepository extends PersonRepository<User, UUID> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("from Delivery d where d.room.id = ?1")
    List<Delivery> findAllDeliveriesByRoomId(Long id);
}
