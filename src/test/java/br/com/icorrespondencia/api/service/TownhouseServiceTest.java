package br.com.icorrespondencia.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import br.com.icorrespondencia.api.util.TownhouseCreator;
import br.com.icorrespondencia.api.util.TownhouseDTOCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Service: Townhouse tests")
class TownhouseServiceTest {

    @InjectMocks
    TownhouseService townhouseService;

    @Mock
    TownhouseRepository townhouseRepositoryMock;

    @Mock
    TownhouseMapper mapper;

    @BeforeEach
    void setup() {
        Townhouse townhouseToBeReturned = TownhouseCreator.valid();

        when(townhouseRepositoryMock.findAllByExcludedAtIsNull())
            .thenReturn(List.of(townhouseToBeReturned));

        when(townhouseRepositoryMock.getOneByIdAndExcludedAtIsNull(1L))
            .thenReturn(Optional.of(townhouseToBeReturned));

        when(mapper.toDomain(any()))
            .thenReturn(townhouseToBeReturned);

        when(mapper.toDTO(any()))
            .thenReturn(TownhouseDTOCreator.valid());
    }

    @Test
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        doNothing()
            .when(townhouseRepositoryMock).excludeAndDeactivateById(1L);

        assertThatCode(() -> townhouseService.destroy(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("index should returns TownhouseDTO list when successful")
    void index_ShouldReturnsTownhouseDTOList_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.valid();

        List<TownhouseDTO> townhouseResult = townhouseService.index();

        assertThat(townhouseResult)
            .isNotNull()
            .element(0)
                .isInstanceOf(TownhouseDTO.class)
                .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("store should returns TownhouseDTO when successful")
    void store_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO toBeStored = TownhouseDTOCreator.toBeStored();

        when(townhouseRepositoryMock.save(mapper.toDomain(TownhouseDTOCreator.toBeStored())))
            .thenReturn(TownhouseCreator.valid());

        TownhouseDTO townhouseExpected = TownhouseDTOCreator.valid();

        TownhouseDTO townhouseResult = townhouseService.store(toBeStored);

        assertThat(townhouseResult)
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("show should returns TownhouseDTO when successful")
    void show_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseExpected = TownhouseDTOCreator.valid();

        TownhouseDTO townhouseResult = townhouseService.show(1L);

        assertThat(townhouseResult)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .isEqualTo(townhouseExpected);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> townhouseService.update(TownhouseDTOCreator.updated()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("show should throw ResourceNotFoundException when id not found")
    void show_ShouldThrowResourceNotFoundException_WhenIdNotFound() {
        when(townhouseRepositoryMock.getOneByIdAndExcludedAtIsNull(2L))
            .thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
            .isThrownBy(() -> townhouseService.show(2L))
            .withMessageContaining("Resource not found");
    }
}
