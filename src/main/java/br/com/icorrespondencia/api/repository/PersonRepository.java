package br.com.icorrespondencia.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository of person inheritance
 *
 * @author Luís Paulino
 * @since 0.2
 * @version 1.0
 */
@NoRepositoryBean
public interface PersonRepository<E, K> extends JpaRepository<E, K> {

    /**
     * {@literal List} all instances not marked as excluded
     *
     * @return entities list
     */
    List<E> findAllByExcludedAtIsNull();

    /**
     * Get one instance not marked as excluded
     *
     * @param id to be searched
     * @return the entity with the given id or {@literal Optional#empty()} if none
     *         found.
     */
    Optional<E> getOneByIdAndExcludedAtIsNull(K id);

    /**
     * Set entity excluded and deactivate
     *
     * @param id to be updated
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update #{#entityName} t set t.excludedAt = CURRENT_TIMESTAMP, t.active = false where t.id = ?1")
    void excludeAndDeactivateById(K id);

    /**
     * Set entity inactive
     *
     * @param id to be deactivated
     * @return number of affected records
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update #{#entityName} t set t.active = false where t.id = ?1 and t.active = true")
    int deactivateById(K id);

     /**
     * Set entity active
     *
     * @param id to be activated
     * @return number of affected records
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update #{#entityName} t set t.active = true where t.id = ?1 and t.active = false")
    int activateById(K id);
}
