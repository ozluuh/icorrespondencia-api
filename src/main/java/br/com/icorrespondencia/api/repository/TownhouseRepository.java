package br.com.icorrespondencia.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icorrespondencia.api.domain.Townhouse;

@Repository
public interface TownhouseRepository extends CrudRepository<Townhouse, Long> {

    List<Townhouse> findAllByExcludedAtIsNull();

    Townhouse getOneByIdAndExcludedAtIsNull(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Townhouse t set t.excludedAt = ?1, t.active = ?2 where t.id = ?3")
    void excludeAndDeactivateFor(LocalDateTime excludedAt, boolean active, Long id);
}
