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
import br.com.icorrespondencia.api.repository.UserRepository;
import br.com.icorrespondencia.api.utils.UserCreator;

@ExtendWith(SpringExtension.class)
@DisplayName("Service: User tests")
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repo;

    @BeforeEach
    void setup() {
        User expectedReturn = UserCreator.valid();

        when(repo.findAllByExcludedAtIsNull())
            .thenReturn(List.of(expectedReturn));

        when(repo.getOneByIdAndExcludedAtIsNull(1L))
            .thenReturn(Optional.of(expectedReturn));

        when(repo.save(any(User.class)))
            .thenReturn(expectedReturn);

        doNothing()
            .when(repo).excludeAndDeactivateById(1L);
    }

    @Test
    @DisplayName("context loads should verify satisfied dependencies when successful")
    void contextLoads_ShouldVerifySatisfiedDependencies_WhenSuccessful() {
        assertThat(service).isNotNull();

        assertThat(repo).isNotNull();
    }

    @Test
    @DisplayName("index should return User list when successful")
    void index_ShouldReturnUserList_WhenSuccessful() {
        User expected = UserCreator.valid();

        List<User> result = service.index();

        assertThat(result)
            .isNotNull()
            .element(0)
                .isInstanceOf(User.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("show should return User when successful")
    void show_ShouldReturnUser_WhenSuccessful() {
        User expected = UserCreator.valid();

        User result = service.show(1L);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(User.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("store should return a valid user when successful")
    void store_ShouldReturnValidUser_WhenSuccessful() {
        User expected = UserCreator.valid();

        User toBeStored = UserCreator.store();

        User result = service.store(toBeStored);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(User.class)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("update should not throw any exception when successful")
    void update_ShouldNotThrowAnyException_WhenSuccessful() {
        User valid = UserCreator.valid();

        assertThatCode(() -> service.update(valid))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("destroy should not throw any exception when successful")
    void destroy_ShouldNotThrowAnyException_WhenSuccessful() {
        assertThatCode(() -> service.destroy(1L))
            .doesNotThrowAnyException();
    }
}
