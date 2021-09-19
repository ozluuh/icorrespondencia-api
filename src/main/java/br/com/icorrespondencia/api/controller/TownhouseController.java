package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

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

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.dto.validation.ValidationGroups;
import br.com.icorrespondencia.api.dto.validation.View;
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
public class TownhouseController implements CrudController<TownhouseDTO, Long> {

    private final TownhouseService service;

    @GetMapping
    @JsonView(View.Public.class)
    @Override
    public ResponseEntity<List<TownhouseDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<TownhouseDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroy(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Override
    public ResponseEntity<TownhouseDTO> store(
            @RequestBody @Validated(ValidationGroups.Post.class) TownhouseDTO entity, UriComponentsBuilder uriBuilder) {

        TownhouseDTO body = service.store(entity);

        URI uri = uriBuilder.path("/townhouses/{id}").buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).body(body);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> update(@RequestBody @Validated(ValidationGroups.Put.class) TownhouseDTO entity) {

        service.update(entity);

        return ResponseEntity.noContent().build();
    }
}
