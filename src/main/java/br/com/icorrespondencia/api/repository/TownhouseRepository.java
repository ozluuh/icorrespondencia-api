package br.com.icorrespondencia.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icorrespondencia.api.domain.Townhouse;

/**
 * Layer to access data objects in repository
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Repository
public interface TownhouseRepository extends CrudRepository<Townhouse, Long> {

    /**
     * {@literal List} all instances not marked as excluded
     *
     * @return entities list
     */
    List<Townhouse> findAllByExcludedAtIsNull();

    /**
     * Get one instance not marked as excluded
     *
     * @param id to be searched
     * @return the entity with the given id or {@literal Optional#empty()} if none
     *         found.
     */
    Optional<Townhouse> getOneByIdAndExcludedAtIsNull(Long id);

    /**
     * Set entity excluded and deactivate
     *
     * @param id to be updated
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Townhouse t set t.excludedAt = CURRENT_TIMESTAMP, t.active = false where t.id = :id")
    void excludeAndDeactivateById(@Param("id") Long id);
}
