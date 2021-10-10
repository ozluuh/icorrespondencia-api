package br.com.icorrespondencia.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.utils.TownhouseCreator;

@DataJpaTest
@DisplayName("Repository: Townhouse tests")
class TownhouseRepositoryTest {

    @Autowired
    TownhouseRepository townhouseRepository;

    @BeforeEach
    void setup() {
        townhouseRepository.deleteAll();
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(townhouseRepository).isNotNull();
    }

    @Test
    @DisplayName("findAllByExcludedAtIsNull should returns townhouses list when successful")
    void findAllByExcludedAtIsNull_ShouldReturnsListOfTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = townhouseRepository.save(TownhouseCreator.store());

        List<Townhouse> townhousesResult = townhouseRepository.findAllByExcludedAtIsNull();

        assertThat(townhousesResult)
            .isInstanceOf(List.class)
            .isNotEmpty()
            .element(0)
                .isInstanceOf(Townhouse.class)
                .extracting(Townhouse::getExcludedAt, Townhouse::getId, Townhouse::getCnpj)
                    .containsExactly(null, townhouseExpected.getId(), townhouseExpected.getCnpj());
    }

    @Test
    @DisplayName("findAllByExcludedAtIsNull should returns empty list when successful")
    void findAllByExcludedAtIsNull_ShouldReturnsEmptyList_WhenSuccessful() {
        List<Townhouse> townhousesResult = townhouseRepository.findAllByExcludedAtIsNull();

        assertThat(townhousesResult)
            .isInstanceOf(List.class)
            .isEmpty();
    }

    @Test
    @DisplayName("getOneByIdAndExcludedAtIsNull should returns townhouse when successful")
    void getOneByIdAndExcludedAtIsNull_ShouldReturnsTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = townhouseRepository.save(TownhouseCreator.store());

        Townhouse townhouseResult = townhouseRepository.getOneByIdAndExcludedAtIsNull(townhouseExpected.getId()).get();

        assertThat(townhouseResult)
            .isNotNull()
            .isInstanceOf(Townhouse.class)
            .extracting(Townhouse::getExcludedAt, Townhouse::getId, Townhouse::getCnpj)
                .containsExactly(null, townhouseExpected.getId(), townhouseExpected.getCnpj());
    }

    @Test
    @DisplayName("excludeAndDeactivateById should assign excludeAt date and deactivate townhouse when successful")
    void excludeAndDeactivateById_ShouldAssignExcludeAtDateAndDeactivateTownhouse_WhenSuccessful() {
        Townhouse townhouseExpected = townhouseRepository.save(TownhouseCreator.store());

        townhouseRepository.excludeAndDeactivateById(townhouseExpected.getId());

        Townhouse townhouseResult = townhouseRepository.findById(townhouseExpected.getId()).get();

        assertThat(townhouseResult)
            .isNotNull()
            .isInstanceOf(Townhouse.class)
            .hasFieldOrProperty("excludedAt")
                .isNotNull()
            .extracting(Townhouse::getId, Townhouse::getCnpj)
                .containsExactly(townhouseExpected.getId(), townhouseExpected.getCnpj());
    }
}
