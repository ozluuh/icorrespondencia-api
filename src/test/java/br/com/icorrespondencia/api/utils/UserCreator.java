package br.com.icorrespondencia.api.utils;

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.dto.UserDTO;
import br.com.icorrespondencia.api.mapper.UserMapper;

public class UserCreator {

    private static final UserDTO mapper(User user){
        return UserMapper.INSTANCE.toDTO(user);
    }

    public static final User store() {
        return User
                    .builder()
                    .name("Peter Parker")
                    .email("parkerpeter@marvel.com")
                    .username("omiranha")
                    .password("aguentaimj")
                .build();
    }

    public static final User valid() {
        User stored = store();

        stored.setId(1L);

        return stored;
    }

    public static final UserDTO storeDTO() {
        return mapper(store());
    }

    public static final UserDTO validDTO() {
        return mapper(valid());
    }
}
