package br.com.icorrespondencia.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.util.TownhouseCreator;

@DataJpaTest
@DisplayName("Townhouse repository tests")
class TownhouseRepositoryTest {

    @Autowired
    TownhouseRepository dao;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    @Test
    @DisplayName("it should verify if all dependencies are satisfied")
    void testSatisfiedDependencies(){
        assertThat(dao).isNotNull();
    }

    @Test
    @DisplayName("it should find all townhouses by excludedAt is null")
    void testFindAllByExcludedIsNull() {
        Townhouse townhouse = dao.save(TownhouseCreator.townhouseToBeStored());

        List<Townhouse> townhouses = dao.findAllByExcludedIsNull();

        assertThat(townhouses)
            .contains(townhouse)
            .element(0)
            .hasFieldOrPropertyWithValue("excludedAt", null)
            .hasFieldOrPropertyWithValue("id", townhouse.getId())
            .hasFieldOrPropertyWithValue("nin", townhouse.getNin());
    }

    @Test
    @DisplayName("it should get one townhouse instance by Id and excludedAt is null")
    void testGetOneByIdAndExcludedAtIsNull() {
        Townhouse townhouse = dao.save(TownhouseCreator.townhouseToBeStored());
        Townhouse townhouseFound = dao.getOneByIdAndExcludedAtIsNull(townhouse.getId());

        assertThat(townhouseFound)
            .isNotNull()
            .hasFieldOrPropertyWithValue("excludedAt", null)
            .hasFieldOrPropertyWithValue("id", townhouse.getId())
            .hasFieldOrPropertyWithValue("nin", townhouse.getNin());
    }

    @Test
    @DisplayName("it should set townhouse excluded and inactive by id")
    void testSetExcludedAndInactiveForTownhouse() {
        Townhouse townhouse = dao.save(TownhouseCreator.townhouseToBeStored());

        LocalDateTime excludedAt = LocalDateTime.now();
        townhouse.setExcludedAt(excludedAt);
        townhouse.setActive(false);

        dao.save(townhouse);

        Townhouse townhouseResult = dao.getOne(townhouse.getId());

        assertThat(townhouseResult)
            .hasFieldOrPropertyWithValue("excludedAt", excludedAt)
            .hasFieldOrPropertyWithValue("active", false)
            .hasFieldOrPropertyWithValue("id", townhouse.getId())
            .hasFieldOrPropertyWithValue("nin", townhouse.getNin());
    }

    @Test
    @DisplayName("it should returns empty list")
    void testEmptyRepository(){
        List<Townhouse> townhouses = dao.findAll();

        assertThat(townhouses).isEmpty();
    }
}
