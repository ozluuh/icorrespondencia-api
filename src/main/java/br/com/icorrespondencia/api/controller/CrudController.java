package br.com.icorrespondencia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Endpoint layer that provide basic crud contract for HTTP verbs
 *
 * @author Luís Paulino
 * @since 0.2
 * @version 1.0
 */
public interface CrudController<E, K> extends Endpoint<E, K> {

    /**
     * Endpoint that remove given id
     *
     * @param id to be removed
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when successful or
     *         {@link org.springframework.http.HttpStatus#BAD_REQUEST 400 Bad
     *         Request} if not present
     */
    ResponseEntity<Void> destroy(K id);

    /**
     * Endpoint that store given entity
     *
     * @param entity     to be stored
     * @param uriBuilder injected by springboot to construct URI
     * @return {@link org.springframework.http.HttpStatus#CREATED 201 Created} when
     *         entity successfully stored or
     *         {@link org.springframework.http.HttpStatus#UNPROCESSABLE_ENTITY 422
     *         Unprocessable Entity} if has validation constraints
     */
    ResponseEntity<E> store(E entity, UriComponentsBuilder uriBuilder);

    /**
     * Endpoint that update given entity
     *
     * @param entity to be updated
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when entity successfully stored or
     *         {@link org.springframework.http.HttpStatus#UNPROCESSABLE_ENTITY 422
     *         Unprocessable Entity} if has validation constraints
     */
    ResponseEntity<Void> update(E entity);
}
