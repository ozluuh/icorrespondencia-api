package br.com.icorrespondencia.api.utils;

import br.com.icorrespondencia.api.domain.User;

public class UserCreator {

    public static final User store() {
        User user = new User();

        user.setName("Peter Parker");
        user.setEmail("parkerpeter@marvel.com");
        user.setUsername("omiranha");
        user.setPassword("aguentaimj");

        user.setCreatedAt(null);
        user.setPublicId(null);

        return user;
    }

    public static final User valid() {
        User user = store();

        user.setId(1L);

        return user;
    }
}
