package br.com.icorrespondencia.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
import br.com.icorrespondencia.api.exception.BadRequestException;
import br.com.icorrespondencia.api.exception.UnprocessableEntityException;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.util.TownhouseCreator;
import br.com.icorrespondencia.api.util.TownhouseDTOCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Service: Townhouse tests")
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

        given(repositoryMock.getOneByIdAndExcludedAtIsNull(ArgumentMatchers.anyLong()))
            .willReturn(Optional.of(townhouseValid));

        given(repositoryMock.save(ArgumentMatchers.any(Townhouse.class)))
            .willReturn(townhouseValid);
    }

    @Test
    @DisplayName("destroyOrThrowBadRequestException should not throw any exception when successful")
    void destroyOrThrowBadRequestException_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.destroyOrThrowBadRequestException(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("index should returns TownhouseDTO list when successful")
    void index_ShouldReturnsListOfTownhouseDTO_WhenSuccessful() {
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
    @DisplayName("storeOrThrowUnprocessableEntityException should returns TownhouseDTO when successful")
    void storeOrThrowUnprocessableEntityException_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service
            .storeOrThrowUnprocessableEntityException(TownhouseDTOCreator.townhouseDTOToBeStored());

        assertThat(townhouseObtained)
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("showOrThrowBadRequestException should returns TownhouseDTO when successful")
    void showOrThrowBadRequestException_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service.showOrThrowBadRequestException(1L);

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("updateOrThrowBadRequestException should not throw any exception when successful")
    void updateOrThrowBadRequestException_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.updateOrThrowBadRequestException(TownhouseDTOCreator.townhouseDTOUpdated()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("showOrThrowBadRequestException should throw BadRequestException when townhouse not found")
    void showOrThrowBadRequestException_ShouldThrowBadRequestException_WhenTownhouseNotFound() {
        given(repositoryMock.getOneByIdAndExcludedAtIsNull(ArgumentMatchers.anyLong()))
            .willReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
            .isThrownBy(() -> service.showOrThrowBadRequestException(1L))
            .withMessageContaining("Townhouse not found");
    }

    @Test
    @DisplayName("storeOrThrowUnprocessableEntityException should throw UnprocessableEntityException when incorrect payload")
    void storeOrThrowUnprocessableEntityException_ShouldThrowUnprocessableEntityException_WhenIncorrectPayload() {
        TownhouseDTO townhouseToBeSaved = TownhouseDTOCreator.townhouseDTOValid();

        assertThatExceptionOfType(UnprocessableEntityException.class)
            .isThrownBy(() -> service.storeOrThrowUnprocessableEntityException(townhouseToBeSaved))
            .withMessageContaining("Id field not be informed");
    }
}
