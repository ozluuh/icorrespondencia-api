package br.com.icorrespondencia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface Endpoint<E, K> {

    ResponseEntity<List<E>> index();

    ResponseEntity<E> show(K id);
}
