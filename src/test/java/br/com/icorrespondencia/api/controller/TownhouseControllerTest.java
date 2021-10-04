package br.com.icorrespondencia.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.service.TownhouseService;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import br.com.icorrespondencia.api.utils.IntegrationUtil;
import br.com.icorrespondencia.api.utils.TownhouseCreator;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TownhouseController.class)
@DisplayName("Controller: Townhouse tests")
class TownhouseControllerTest {

    private static final String BASE_ENDPOINT = "/townhouses";

    private static final UUID VALID_ID = UUID.randomUUID();

    private static final UUID INVALID_ID = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");

    @MockBean
    TownhouseService service;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        Townhouse expectedReturn = TownhouseCreator.valid();

        expectedReturn.setId(VALID_ID);

        when(service.index())
            .thenReturn(List.of(expectedReturn));

        when(service.show(VALID_ID))
            .thenReturn(expectedReturn);

        when(service.store(any(Townhouse.class)))
            .thenReturn(expectedReturn);

        doNothing()
            .when(service).destroy(VALID_ID);

        when(service.show(INVALID_ID))
            .thenThrow(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(service).isNotNull();

        assertThat(mvc).isNotNull();
    }

    @Test
    @DisplayName("store should response with status 201 when successful")
    void store_ShouldResponse201_WhenSuccessful() throws Exception {
        Townhouse expected = TownhouseCreator.store();

        mvc.perform(
            post(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .content(IntegrationUtil.asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.public_id").exists())
        .andExpect(jsonPath("$.cnpj").value(expected.getCnpj()));
    }

    @Test
    @DisplayName("update should response with status 204 when successful")
    void update_ShouldResponse204_WhenSuccessful() throws Exception {
        Townhouse expected = TownhouseCreator.valid();

        mvc.perform(
            put(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .content(IntegrationUtil.asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("destroy should response with status 204 when successful")
    void destroy_ShouldResponse204_WhenSuccessful() throws Exception {
        doNothing()
            .when(service).destroy(VALID_ID);

        mvc.perform(
            delete(BASE_ENDPOINT + "/{id}", VALID_ID)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("index should response with status 200 when successful")
    void index_ShouldResponse200_WhenSuccessful() throws Exception {
        Townhouse expected = TownhouseCreator.valid();

        mvc.perform(
            get(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].cnpj").value(expected.getCnpj()));
    }

    @Test
    @DisplayName("show should response with status 200 when successful")
    void show_ShouldResponse200_WhenSuccessful() throws Exception {
        Townhouse expected = TownhouseCreator.valid();

        expected.setId(VALID_ID);

        mvc.perform(
            get(BASE_ENDPOINT + "/{id}", VALID_ID)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.public_id").exists())
        .andExpect(jsonPath("$.cnpj").value(expected.getCnpj()));
    }

    @Test
    @DisplayName("store should response with status 422 when payload has restriction conflicts")
    void store_ShouldResponse422_WhenPayloadHasRestrictionConflicts() throws Exception {
        Townhouse expected = TownhouseCreator.valid();

        mvc.perform(
            post(BASE_ENDPOINT)
                .content(IntegrationUtil.asJsonString(expected))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("show should response with status 400 when resource not found")
    void show_ShouldResponse400_WhenResourceNotFound() throws Exception {
        mvc.perform(
            get(BASE_ENDPOINT + "/{id}", INVALID_ID)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }
}
