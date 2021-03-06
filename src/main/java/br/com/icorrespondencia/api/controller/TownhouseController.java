package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.icorrespondencia.api.domain.Delivery;
import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.domain.validation.ValidationGroups;
import br.com.icorrespondencia.api.domain.validation.View;
import br.com.icorrespondencia.api.service.TownhouseService;

import lombok.RequiredArgsConstructor;

/**
 * Townhouse controller that implements {@link Endpoint} contract for HTTP verbs
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/townhouses")
public class TownhouseController implements CrudController<Townhouse, UUID> {

    private final TownhouseService service;

    @GetMapping
    @JsonView(View.Public.class)
    @Override
    public ResponseEntity<List<Townhouse>> index() {
        return ResponseEntity.ok(service.index());
    }

    @GetMapping(path = "/{id}")
    @JsonView(View.Private.class)
    @Override
    public ResponseEntity<Townhouse> show(@PathVariable UUID id) {
        return ResponseEntity.ok(service.show(id));
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> destroy(@PathVariable UUID id) {
        service.destroy(id);

        return ResponseEntity.noContent().build();
    }

    @JsonView(View.Public.class)
    @PostMapping
    @Override
    public ResponseEntity<Townhouse> store(
            @RequestBody @Validated(ValidationGroups.Post.class) Townhouse entity,
            UriComponentsBuilder uriBuilder
    ) {

        Townhouse body = service.store(entity);

        URI uri = uriBuilder
                        .path("/townhouses/{id}")
                        .buildAndExpand(entity.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(body);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> update(@RequestBody @Validated(ValidationGroups.Put.class) Townhouse entity) {

        service.update(entity);

        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint that deactivate given entity by id
     *
     * @param id to be deactivated
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when entity successfully deactivated or
     *         {@link org.springframework.http.HttpStatus#BAD_REQUEST 400 Bad
     *         Request} if the townhouse has been previously disabled
     */
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        boolean isDeactivated = service.deactivate(id);

        if (!isDeactivated) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

     /**
     * Endpoint that activate given entity by id
     *
     * @param id to be activated
     * @return {@link org.springframework.http.HttpStatus#NO_CONTENT 204 No Content}
     *         when entity successfully activated or
     *         {@link org.springframework.http.HttpStatus#BAD_REQUEST 400 Bad
     *         Request} if the townhouse has been previously enabled
     */
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        boolean isActivate = service.activate(id);

        if (!isActivate) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mailings")
    public ResponseEntity<Void> mailings(@PathVariable UUID id, @RequestBody Delivery delivery) {
        service.mailings(delivery, id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
