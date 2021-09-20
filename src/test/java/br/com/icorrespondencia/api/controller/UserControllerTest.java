package br.com.icorrespondencia.api.controller;

import static br.com.icorrespondencia.api.utils.IntegrationUtil.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.service.UserService;
import br.com.icorrespondencia.api.utils.UserCreator;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@DisplayName("Controller: User tests")
public class UserControllerTest {

    @MockBean
    UserService service;

    @Autowired
    MockMvc mvc;

    static final String BASE_ENDPOINT = "/users";

    @BeforeEach
    void setUp() {
        User expectedReturn = UserCreator.valid();

        when(service.index())
            .thenReturn(List.of(expectedReturn));

        when(service.show(1L))
            .thenReturn(expectedReturn);

        when(service.store(any(User.class)))
            .thenReturn(expectedReturn);

        doNothing()
            .when(service).destroy(1L);
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(service).isNotNull();

        assertThat(mvc).isNotNull();
    }


    @Test
    @DisplayName("index should response with status 200 when successful")
    void index_ShouldResponse200_WhenSuccessful() throws Exception {
        User expected = UserCreator.valid();

        mvc.perform(
            get(BASE_ENDPOINT)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].publicId").exists())
        .andExpect(jsonPath("$.[0].username").value(expected.getUsername()));
    }

    @Test
    @DisplayName("show should response with status 200 when successful")
    void show_ShouldResponse200_WhenSuccessful() throws Exception {
        User expected = UserCreator.valid();

        mvc.perform(
            get(BASE_ENDPOINT + "/{id}", 1L)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(jsonPath("$.publicId").exists())
        .andExpect(jsonPath("$.username").value(expected.getUsername()))
        .andExpect(jsonPath("$.password").value(expected.getPassword()));
    }

    @Test
    @DisplayName("store should response with status 201 when successful")
    void store_ShouldResponse201_WhenSuccessful() throws Exception {
        User expected = UserCreator.valid();

        mvc.perform(
            post(BASE_ENDPOINT)
            .content(asJsonString(expected))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.publicId").exists())
        .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("destroy should response with status 204 when successful")
    void destroy_ShouldResponse204_WhenSuccessful() throws Exception {
        mvc.perform(
            delete(BASE_ENDPOINT + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("update should response with status 204 when successful")
    void update_ShouldResponse204_WhenSuccessful() throws Exception {
        User expected = UserCreator.valid();

        mvc.perform(
            put(BASE_ENDPOINT)
                .content(asJsonString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isNoContent());
    }
}
