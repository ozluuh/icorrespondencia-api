package br.com.icorrespondencia.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icorrespondencia.api.domain.Townhouse;

@Repository
public interface TownhouseRepository extends CrudRepository<Townhouse, Long> {

    List<Townhouse> findAllByExcludedAtIsNull();

    Townhouse getOneByIdAndExcludedAtIsNull(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Townhouse t set t.excludedAt = CURRENT_TIMESTAMP, t.active = false where t.id = :id")
    void excludeAndDeactivateById(@Param("id") Long id);
}
