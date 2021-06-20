package br.com.icorrespondencia.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.icorrespondencia.api.domain.Townhouse;

@Repository
public interface TownhouseRepository extends JpaRepository<Townhouse, Long> {

    @Query("from Townhouse t where t.excludedAt is null")
    List<Townhouse> findAllByExcludedIsNull();

    Townhouse getOneByIdAndExcludedAtIsNull(Long id);
}
