package br.com.icorrespondencia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

/**
 * Endpoint layer that stipulates the contract only for the HTTP GET verb as it
 * is mandatory
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
public interface Endpoint<E, K> {

    /**
     * Endpoint that list all entities
     *
     * @return {@link org.springframework.http.HttpStatus#OK 200 OK} with entities
     *         list or empty list.
     */
    ResponseEntity<List<E>> index();

    /**
     * Endpoint that show given entity
     *
     * @param id to be showed
     * @return {@link org.springframework.http.HttpStatus#OK 200 OK} when entity
     *         found or {@link org.springframework.http.HttpStatus#BAD_REQUEST 400
     *         Bad Request} if not found
     */
    ResponseEntity<E> show(K id);
}
