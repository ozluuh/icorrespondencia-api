package br.com.icorrespondencia.api.service;

import java.util.List;

public interface Business<E, K> {
    List<E> index();

    E show(K id);
}
