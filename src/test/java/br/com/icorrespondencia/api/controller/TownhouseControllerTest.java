package br.com.icorrespondencia.api.controller;

import static br.com.icorrespondencia.api.utils.IntegrationUtil.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.service.TownhouseService;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import br.com.icorrespondencia.api.utils.TownhouseDTOCreator;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TownhouseController.class)
@DisplayName("Controller: Townhouse tests")
class TownhouseControllerTest {

    @MockBean
    TownhouseService townhouseServiceMock;

    @Autowired
    MockMvc mvc;

    final String BASE_ENDPOINT = "/townhouses";

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(townhouseServiceMock).isNotNull();

        assertThat(mvc).isNotNull();
    }

    @Test
    @DisplayName("store should response with status 201 when successful")
    void store_ShouldResponse201_WhenSuccessful() throws Exception {
        TownhouseDTO townhouseResult = TownhouseDTOCreator.valid();

        TownhouseDTO townhouseToBeStored = TownhouseDTOCreator.toBeStored();

        when(townhouseServiceMock.store(townhouseToBeStored))
            .thenReturn(townhouseResult);

        mvc.perform(
            post(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .content(asJsonString(townhouseToBeStored))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.cnpj").value(townhouseToBeStored.getCnpj()));
    }

    @Test
    @DisplayName("update should response with status 204 when successful")
    void update_ShouldResponse204_WhenSuccessful() throws Exception {
        TownhouseDTO townhouseToBeUpdated = TownhouseDTOCreator.updated();

        doNothing()
            .when(townhouseServiceMock).update(townhouseToBeUpdated);

        mvc.perform(
            put(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .content(asJsonString(townhouseToBeUpdated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("destroy should response with status 204 when successful")
    void destroy_ShouldResponse204_WhenSuccessful() throws Exception {
        doNothing()
            .when(townhouseServiceMock).destroy(1L);

        mvc.perform(
            delete(BASE_ENDPOINT + "/{id}", 1L)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("index should response with status 200 when successful")
    void index_ShouldResponse200_WhenSuccessful() throws Exception {
        TownhouseDTO townhouseResult = TownhouseDTOCreator.valid();

        when(townhouseServiceMock.index()).thenReturn(List.of(townhouseResult));

        mvc.perform(
            get(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].cnpj").value(townhouseResult.getCnpj()));
    }

    @Test
    @DisplayName("show should response with status 200 when successful")
    void show_ShouldResponse200_WhenSuccessful() throws Exception {
        TownhouseDTO townhouseResult = TownhouseDTOCreator.valid();

        when(townhouseServiceMock.show(1L)).thenReturn(townhouseResult);

        mvc.perform(
            get(BASE_ENDPOINT + "/{id}", 1L)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.cnpj").value(townhouseResult.getCnpj()));
    }

    @Test
    @DisplayName("store should response with status 422 when payload has restriction conflicts")
    void store_ShouldResponse422_WhenPayloadHasRestrictionConflicts() throws Exception {
        TownhouseDTO townhouseToBeStored = TownhouseDTOCreator.valid();

        mvc.perform(
            post(BASE_ENDPOINT)
                .content(asJsonString(townhouseToBeStored))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("show should response with status 400 when resource not found")
    void show_ShouldResponse400_WhenResourceNotFound() throws Exception {
        when(townhouseServiceMock.show(1L)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(
            get(BASE_ENDPOINT + "/{id}", 1L)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }
}
