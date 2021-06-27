package br.com.icorrespondencia.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.service.TownhouseService;
import br.com.icorrespondencia.api.util.TownhouseDTOCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Controller: Townhouse tests")
class TownhouseControllerTest {

    @InjectMocks
    TownhouseController controller;

    @Mock
    TownhouseService serviceMock;

    @BeforeEach
    void setup() {
        TownhouseDTO townhouseDTOValid = TownhouseDTOCreator.townhouseDTOValid();

        given(serviceMock.index())
            .willReturn(List.of(townhouseDTOValid));

        given(serviceMock.show(ArgumentMatchers.anyLong()))
            .willReturn(townhouseDTOValid);

        given(serviceMock.store(ArgumentMatchers.any(TownhouseDTO.class)))
            .willReturn(townhouseDTOValid);

        doNothing()
            .when(serviceMock)
                .update(ArgumentMatchers.any(TownhouseDTO.class));

        doNothing()
            .when(serviceMock)
                .destroy(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("inject should verify if all dependencies are satisfied")
    void inject_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(controller).isNotNull();

        assertThat(serviceMock).isNotNull();
    }

    @Test
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> controller.destroy(1L))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("destroy should response NO_CONTENT status code when successful")
    void destroy_ShouldResponseNoContentStatusCode_WhenSuccessful() {
        ResponseEntity<Void> responseObtained = controller.destroy(1L);

        assertThat(responseObtained.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("index should returns TownhouseDTO list when successful")
    void index_ShouldReturnsListOfTownhouseDTO_WhenSuccessful() {
        TownhouseDTO townhouseValid = TownhouseDTOCreator.townhouseDTOValid();

        List<TownhouseDTO> responseObtained = controller.index().getBody();

        assertThat(responseObtained)
            .isNotNull()
            .isInstanceOf(List.class)
            .hasSize(1)
            .contains(townhouseValid);
    }

    @Test
    @DisplayName("index should response OK status code when successful")
    void index_ShouldResponseOkStatusCode_WhenSuccessful() {
        ResponseEntity<List<TownhouseDTO>> responseObtained = controller.index();

        assertThat(responseObtained.getStatusCode())
            .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> controller.update(TownhouseDTOCreator.townhouseDTOUpdated()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update should response NO_CONTENT status code when successful")
    void update_ShouldResponseNoContentStatusCode_WhenSuccessful() {
        ResponseEntity<Void> responseObtained = controller.update(TownhouseDTOCreator.townhouseDTOUpdated());

        assertThat(responseObtained.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("show should returns TownhouseDTO when successful")
    void show_ShouldReturnsTownhouseDTO_WhenSuccessful() {
        Long expectedId = TownhouseDTOCreator.townhouseDTOValid().getId();

        TownhouseDTO responseObtained = controller.show(1L).getBody();

        assertThat(responseObtained)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .extracting(TownhouseDTO::getId)
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("show should response OK status code when successful")
    void show_ShouldResponseOkStatusCode_WhenSuccessful() {
        ResponseEntity<TownhouseDTO> responseObtained = controller.show(1L);

        assertThat(responseObtained.getStatusCode())
            .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("store should returns TownhouseDTO with Id non null when successful")
    void store_ShouldReturnsTownhouseDTOWithIdNonNull_WhenSuccessful() {
        TownhouseDTO townhouseToBeStored = TownhouseDTOCreator.townhouseDTOToBeStored();

        TownhouseDTO responseObtained = controller.store(townhouseToBeStored).getBody();

        assertThat(responseObtained)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .hasFieldOrProperty("id")
                .isNotNull()
            .extracting(TownhouseDTO::getCnpj)
                .isEqualTo(townhouseToBeStored.getCnpj());
    }

    @Test
    @DisplayName("store should response CREATED status code when successful")
    void store_ShouldResponseCreatedStatusCode_WhenSuccessful() {
        ResponseEntity<TownhouseDTO> responseObtained = controller.store(TownhouseDTOCreator.townhouseDTOToBeStored());

        assertThat(responseObtained.getStatusCode())
            .isEqualTo(HttpStatus.CREATED);
    }
}
