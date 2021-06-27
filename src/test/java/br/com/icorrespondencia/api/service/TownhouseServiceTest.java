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
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.InvalidPayloadException;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
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
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.destroy(1L))
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
    @DisplayName("store should returns TownhouseDTO when successful")
    void store_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service.store(TownhouseDTOCreator.townhouseDTOToBeStored());

        assertThat(townhouseObtained)
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("show should returns TownhouseDTO when successful")
    void show_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.townhouseDTOValid();

        TownhouseDTO townhouseObtained = service.show(1L);

        assertThat(townhouseObtained)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.update(TownhouseDTOCreator.townhouseDTOUpdated()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("show should throw ResourceNotFoundException when id not found")
    void show_ShouldThrowResourceNotFoundException_WhenIdNotFound() {
        given(repositoryMock.getOneByIdAndExcludedAtIsNull(ArgumentMatchers.anyLong()))
            .willReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
            .isThrownBy(() -> service.show(1L))
            .withMessageContaining("Resource not found");
    }

    @Test
    @DisplayName("store should throw InvalidPayloadException when payload has id filled")
    void store_ShouldThrowInvalidPayloadException_WhenPayloadHasIdFilled() {
        TownhouseDTO townhouseWithIdFilled = TownhouseDTOCreator.townhouseDTOValid();

        assertThatExceptionOfType(InvalidPayloadException.class)
            .isThrownBy(() -> service.store(townhouseWithIdFilled))
            .withMessageContaining("id field should not be informed");
    }
}
