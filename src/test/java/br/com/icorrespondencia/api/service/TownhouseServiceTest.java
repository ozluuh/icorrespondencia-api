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
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import br.com.icorrespondencia.api.utils.TownhouseCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Service: Townhouse tests")
class TownhouseServiceTest {

    @InjectMocks
    TownhouseService service;

    @Mock
    TownhouseRepository repo;

    @BeforeEach
    void setUp() {
        Townhouse expectedReturn = TownhouseCreator.valid();

        when(repo.findAllByExcludedAtIsNull())
            .thenReturn(List.of(expectedReturn));

        when(repo.getOneByIdAndExcludedAtIsNull(1L))
            .thenReturn(Optional.of(expectedReturn));

        doNothing()
            .when(repo).excludeAndDeactivateById(1L);

        when(repo.save(any(Townhouse.class)))
            .thenReturn(expectedReturn);

        when(repo.getOneByIdAndExcludedAtIsNull(-1L))
            .thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(service).isNotNull();

        assertThat(repo).isNotNull();
    }

    @Test
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.destroy(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("index should returns Townhouse list when successful")
    void index_ShouldReturnsTownhouseList_WhenSuccessful() {
        Townhouse expected = TownhouseCreator.valid();

        List<Townhouse> result = service.index();

        assertThat(result)
            .isNotNull()
            .element(0)
                .isInstanceOf(Townhouse.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("store should returns Townhouse when successful")
    void store_ShouldReturnsTownhouse_WhenSuccessful() {
        Townhouse toBeStored = TownhouseCreator.store();

        Townhouse expected = TownhouseCreator.valid();

        Townhouse result = service.store(toBeStored);

        assertThat(result)
            .isInstanceOf(Townhouse.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("show should returns Townhouse when successful")
    void show_ShouldReturnsTownhouse_WhenSuccessful() {
        Townhouse expected = TownhouseCreator.valid();

        Townhouse result = service.show(1L);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(Townhouse.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.update(TownhouseCreator.valid()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("show should throw ResourceNotFoundException when id not found")
    void show_ShouldThrowResourceNotFoundException_WhenIdNotFound() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
            .isThrownBy(() -> service.show(-1L));
    }
}
