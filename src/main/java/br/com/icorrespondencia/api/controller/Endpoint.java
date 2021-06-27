package br.com.icorrespondencia.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface Endpoint<E, K> {

    @GetMapping
    ResponseEntity<List<E>> index();

    @GetMapping(path = "/{id}")
    ResponseEntity<E> show(@PathVariable K id);

    @PostMapping
    default ResponseEntity<E> store(@RequestBody E entity) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping
    default ResponseEntity<Void> update(@RequestBody E entity) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(path = "/{id}")
    default ResponseEntity<Void> destroy(@PathVariable K id) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
