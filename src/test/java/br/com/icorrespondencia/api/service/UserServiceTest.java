package br.com.icorrespondencia.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.dto.UserDTO;
import br.com.icorrespondencia.api.mapper.UserMapper;
import br.com.icorrespondencia.api.repository.UserRepository;
import br.com.icorrespondencia.api.utils.UserCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Service: User tests")
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repo;

    @Mock
    UserMapper mapper;

    @BeforeEach
    void setup() {
        User userMock = UserCreator.valid();

        UserDTO userDTOMock = UserCreator.validDTO();

        when(mapper.toDTO(any(User.class)))
            .thenReturn(userDTOMock);

        when(mapper.toDomain(any(UserDTO.class)))
            .thenReturn(userMock);

        when(repo.findAllByExcludedAtIsNull())
            .thenReturn(List.of(userMock));

        when(repo.getOneByIdAndExcludedAtIsNull(1L))
            .thenReturn(Optional.of(userMock));

        when(repo.save(any(User.class)))
            .thenReturn(userMock);

        doNothing().when(repo).excludeAndDeactivateById(1L);
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(service).isNotNull();

        assertThat(repo).isNotNull();

        assertThat(mapper).isNotNull();
    }

    @Test
    @DisplayName("index should return UserDTO list when successful")
    void index_ShouldReturnUserDTOList_WhenSuccessful() {
        UserDTO expected = UserCreator.validDTO();

        List<UserDTO> result = service.index();

        assertThat(result)
            .isNotNull()
            .element(0)
                .isInstanceOf(UserDTO.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("show should return UserDTO when successful")
    void show_ShouldReturnUserDTO_WhenSuccessful() {
        UserDTO expected = UserCreator.validDTO();

        UserDTO result = service.show(1L);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(UserDTO.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("store should return a valid user when successful")
    void store_ShouldReturnValidUser_WhenSuccessful() {
        UserDTO expected = UserCreator.validDTO();

        UserDTO toBeStored = UserCreator.storeDTO();

        UserDTO result = service.store(toBeStored);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(UserDTO.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        UserDTO validDTO = UserCreator.validDTO();

        assertThatCode(() -> service.update(validDTO))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.destroy(1L))
            .doesNotThrowAnyException();
    }
}
