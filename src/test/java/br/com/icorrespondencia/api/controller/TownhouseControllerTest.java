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
@DisplayName("Townhouse controller tests")
class TownhouseControllerTest {

    @InjectMocks
    TownhouseController controller;

    @Mock
    TownhouseService serviceMock;

    @BeforeEach
    void setUp() {
        TownhouseDTO townhouseDTOValid = TownhouseDTOCreator.townhouseDTOValid();

        given(serviceMock.index())
            .willReturn(List.of(townhouseDTOValid));

        given(serviceMock.showTownhouseOrThrowBadRequestException(ArgumentMatchers.anyLong()))
            .willReturn(TownhouseDTOCreator.townhouseDTOValid());

        given(serviceMock.storeTownhouseOrThrowUnprocessableEntityException(ArgumentMatchers.any(TownhouseDTO.class)))
            .willReturn(TownhouseDTOCreator.townhouseDTOValid());

        doNothing()
            .when(serviceMock)
                .updateTownhouseOrThrowBadRequestException(ArgumentMatchers.any(TownhouseDTO.class));

        doNothing()
            .when(serviceMock)
                .destroyTownhouseOrThrowBadRequestException(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("it should verify all dependencies are satisfied")
    void testSatisfiedDependencies() {
        assertThat(controller).isNotNull();
        assertThat(serviceMock).isNotNull();
    }

    @Test
    @DisplayName("it should return status 204 when successful")
    void testDestroy() {
        assertThatCode(() -> controller.destroy(1L))
            .doesNotThrowAnyException();

        ResponseEntity<Void> destroyEndpointResponse = controller.destroy(1L);

        assertThat(destroyEndpointResponse)
            .isNotNull();

        assertThat(destroyEndpointResponse.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should returns a list of valid TownhouseDTO when successful")
    void testIndex() {
        TownhouseDTO townhouseValid = TownhouseDTOCreator.townhouseDTOValid();
        List<TownhouseDTO> indexEndpointResponse = controller.index().getBody();

        assertThat(indexEndpointResponse)
            .isNotNull()
            .isInstanceOf(List.class)
            .hasSize(1)
            .contains(townhouseValid)
            .element(0)
            .hasFieldOrPropertyWithValue("id", townhouseValid.getId());
    }

    @Test
    @DisplayName("it should return status 204 when successful replaced")
    void testReplace() {

        assertThatCode(() -> controller.replace(TownhouseDTOCreator.townhouseDTOUpdated()))
            .doesNotThrowAnyException();

        TownhouseDTO townhouseToBeUpdated = TownhouseDTOCreator.townhouseDTOUpdated();
        ResponseEntity<Void> replaceEndpointResponse = controller.replace(townhouseToBeUpdated);

        assertThat(replaceEndpointResponse)
            .isNotNull();

        assertThat(replaceEndpointResponse.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("it should return a valid TownhouseDTO when successful displayed")
    void testShow() {
        Long expectedId = TownhouseDTOCreator.townhouseDTOValid().getId();
        TownhouseDTO showEndpointResponse = controller.show(1L).getBody();

        assertThat(showEndpointResponse)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .hasFieldOrPropertyWithValue("id", expectedId);

    }

    @Test
    @DisplayName("it should return a valid TownhouseDTO when successful stored")
    void testStore() {
        TownhouseDTO townhouseToBeStored = TownhouseDTOCreator.townhouseDTOToBeStored();
        TownhouseDTO storeEndpointResponse = controller.store(townhouseToBeStored).getBody();

        assertThat(storeEndpointResponse)
            .isNotNull()
            .isInstanceOf(TownhouseDTO.class)
            .hasFieldOrProperty("id").isNotNull()
            .hasFieldOrPropertyWithValue("name", townhouseToBeStored.getName())
            .hasFieldOrPropertyWithValue("nin", townhouseToBeStored.getNin());
    }
}
