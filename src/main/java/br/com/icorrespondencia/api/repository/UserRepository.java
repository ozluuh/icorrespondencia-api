package br.com.icorrespondencia.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("from Delivery d where d.room.id = ?1 and d.readAt is null order by d.deliveryDate desc")
    List<Delivery> findAllDeliveriesByRoomId(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Delivery d set d.read = true, d.readAt = CURRENT_TIMESTAMP where d.id = ?1")
    int setDeliveryRead(Long id);
}
