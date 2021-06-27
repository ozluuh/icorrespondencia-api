package br.com.icorrespondencia.api.service;

import java.util.List;

public interface Business<E, K> {
    List<E> index();

    E show(K id);

    default void update(E entity) {
    }

    default E store(E entity) {
        return null;
    }

    default void destroy(K id) {
    }
}
