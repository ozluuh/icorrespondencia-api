package br.com.icorrespondencia.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.util.TownhouseCreator;

@DataJpaTest
@DisplayName("Repository: Townhouse tests")
class TownhouseRepositoryTest {

    @Autowired
    TownhouseRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("inject should verify if all dependencies are satisfied")
    void inject_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("findAllByExcludedAtIsNull should returns townhouses list when successful")
    void findAllByExcludedAtIsNull_ShouldReturnsListOfTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = repository.save(TownhouseCreator.townhouseToBeStored());

        List<Townhouse> townhousesObtained = repository.findAllByExcludedAtIsNull();

        assertThat(townhousesObtained)
            .isInstanceOf(List.class)
            .isNotEmpty()
            .element(0)
                .isInstanceOf(Townhouse.class)
                .extracting(Townhouse::getExcludedAt, Townhouse::getId, Townhouse::getNin)
                    .containsExactly(null, townhouseExpected.getId(), townhouseExpected.getNin());
    }

    @Test
    @DisplayName("findAllByExcludedAtIsNull should returns empty list when successful")
    void findAllByExcludedAtIsNull_ShouldReturnsEmptyList_WhenSuccessful() {
        List<Townhouse> townhousesObtained = repository.findAllByExcludedAtIsNull();

        assertThat(townhousesObtained)
            .isInstanceOf(List.class)
            .isEmpty();
    }

    @Test
    @DisplayName("getOneByIdAndExcludedAtIsNull should returns townhouse when successful")
    void getOneByIdAndExcludedAtIsNull_ShouldReturnsTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = repository.save(TownhouseCreator.townhouseToBeStored());

        Townhouse townhouseObtained = repository.getOneByIdAndExcludedAtIsNull(townhouseExpected.getId()).get();

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(Townhouse.class)
            .extracting(Townhouse::getExcludedAt, Townhouse::getId, Townhouse::getNin)
                .containsExactly(null, townhouseExpected.getId(), townhouseExpected.getNin());
    }

    @Test
    @DisplayName("excludeAndDeactivateById should assign excludeAt date and deactivate townhouse when successful")
    void excludeAndDeactivateById_ShouldAssignExcludeAtDateAndDeactivateTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = repository.save(TownhouseCreator.townhouseToBeStored());

        repository.excludeAndDeactivateById(townhouseExpected.getId());

        Townhouse townhouseObtained = repository.findById(townhouseExpected.getId()).get();

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(Townhouse.class)
            .hasFieldOrProperty("excludedAt")
                .isNotNull()
            .extracting(Townhouse::getId, Townhouse::getNin)
                .containsExactly(townhouseExpected.getId(), townhouseExpected.getNin());
    }
}
