package br.com.icorrespondencia.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.exception.ValidationGroups;
import br.com.icorrespondencia.api.service.TownhouseService;

import lombok.RequiredArgsConstructor;

/**
 * Townhouse controller that implements {@link Endpoint} contract for HTTP verbs
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/townhouses")
public class TownhouseController implements Endpoint<TownhouseDTO, Long> {

    private final TownhouseService service;

    @GetMapping
    @Override
    public ResponseEntity<List<TownhouseDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<TownhouseDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    /**
     * Endpoint that remove given id
     *
     * @param id to be removed
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when successful or
     *         {@link org.springframework.http.HttpStatus#BAD_REQUEST 400 Bad
     *         Request} if not present
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroy(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint that store given entity
     *
     * @param entity to be stored
     * @return {@link org.springframework.http.HttpStatus#CREATED 201 Created} when
     *         entity successfully stored or
     *         {@link org.springframework.http.HttpStatus#UNPROCESSABLE_ENTITY 422
     *         Unprocessable Entity} if has validation constraints
     */
    @PostMapping
    public ResponseEntity<TownhouseDTO> store(
            @RequestBody @Validated(ValidationGroups.Post.class) TownhouseDTO entity) {

        return new ResponseEntity<>(service.store(entity), HttpStatus.CREATED);
    }

    /**
     * Endpoint that update given entity
     *
     * @param entity to be stored
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when entity successfully stored or
     *         {@link org.springframework.http.HttpStatus#UNPROCESSABLE_ENTITY 422
     *         Unprocessable Entity} if has validation constraints
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Validated(ValidationGroups.Put.class) TownhouseDTO entity) {

        service.update(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
