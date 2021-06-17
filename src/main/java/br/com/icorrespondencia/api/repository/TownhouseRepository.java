package br.com.icorrespondencia.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icorrespondencia.api.domain.Townhouse;

@Repository
public interface TownhouseRepository extends JpaRepository<Townhouse, Long> {

    @Transactional
    @Modifying
    @Query("update Townhouse t set t.excludedAt = ?1, t.active = ?2 where t = ?3")
    Integer setExcludedAndInactiveFor(LocalDateTime exclude, boolean active, Townhouse townhouse);

    @Query("from Townhouse t where t.excludedAt is null")
    List<Townhouse> findAllByExcludedIsNull();

    Townhouse getOneByIdAndExcludedAtIsNull(Long id);
}
