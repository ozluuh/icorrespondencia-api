package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.domain.validation.View;
import br.com.icorrespondencia.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements CrudController<User, Long> {

    private final UserService service;

    @JsonView(View.Public.class)
    @GetMapping
    @Override
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.ok(service.index());
    }

    @JsonView(View.Internal.class)
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<User> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<User> store(
            @RequestBody User user,
            UriComponentsBuilder uriBuilder
    ) {
        User body = service.store(user);

        URI uri = uriBuilder
                        .path("/users/{id}")
                        .buildAndExpand(body.getPublicId())
                    .toUri();

        return ResponseEntity.created(uri).body(body);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroy(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> update(@RequestBody User entity) {
        service.update(entity);

        return ResponseEntity.noContent().build();
    }

}
