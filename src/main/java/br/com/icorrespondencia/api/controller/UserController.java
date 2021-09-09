package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserController implements Endpoint<UserDTO, Long> {

    private final UserService service;

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> store(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder){
        UserDTO body = service.store(user);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(body.getId()).toUri();

        return ResponseEntity.created(uri).body(body);
    }

}
