package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;

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

import br.com.icorrespondencia.api.dto.UserDTO;
import br.com.icorrespondencia.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements CrudController<UserDTO, Long> {

    private final UserService service;

    @GetMapping
    @Override
    public ResponseEntity<List<UserDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserDTO> store(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder) {
        UserDTO body = service.store(user);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(body.getId()).toUri();

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
    public ResponseEntity<Void> update(@RequestBody UserDTO entity) {
        service.update(entity);

        return ResponseEntity.noContent().build();
    }

}
