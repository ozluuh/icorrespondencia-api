package br.com.icorrespondencia.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.util.TownhouseCreator;
import br.com.icorrespondencia.api.util.TownhouseDTOCreator;

@ExtendWith(SpringExtension.class)
class TownhouseServiceTest {

    @InjectMocks
    TownhouseService service;

    @Mock
    TownhouseRepository repositoryMock;

    @BeforeEach
    void setup() {
        Townhouse townhouseValid = TownhouseCreator.townhouseValid();

        given(repositoryMock.findAllByExcludedAtIsNull())
            .willReturn(List.of(townhouseValid));

        given(repositoryMock.findById(ArgumentMatchers.anyLong()))
            .willReturn(Optional.of(townhouseValid));

        given(repositoryMock.save(ArgumentMatchers.any(Townhouse.class)))
            .willReturn(townhouseValid);
    }

    @Test
    @DisplayName("it should exclude and deactivate Townhouse by id when successful")
    void testDestroyTownhouseOrThrowBadRequestException() {
        assertThatCode(() -> service.destroyTownhouseOrThrowBadRequestException(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("it should returns list of TownhouseDTO when successful")
    void testIndex() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        List<TownhouseDTO> townhouseObtained = service.index();

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(List.class)
            .element(0)
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("it should store townhouse and returns a TownhouseDTO when successful")
    void testStoreTownhouseOrThrowUnprocessableEntityException() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service
            .storeTownhouseOrThrowUnprocessableEntityException(TownhouseDTOCreator.townhouseDTOToBeStored());

        assertThat(townhouseObtained)
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("it should return a TownhouseDTO when successful")
    void testShowTownhouseOrThrowBadRequestException() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service.showTownhouseOrThrowBadRequestException(1L);

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("it should updates Townhouse when successful")
    void testUpdateTownhouseOrThrowBadRequestException() {
        assertThatCode(() -> service.updateTownhouseOrThrowBadRequestException(TownhouseDTOCreator.townhouseDTOUpdated()))
            .doesNotThrowAnyException();
    }
}
