package br.com.icorrespondencia.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import br.com.icorrespondencia.api.controller.exception.KeysNotPresentException;
import br.com.icorrespondencia.api.domain.Delivery;
import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.domain.validation.ValidationGroups;
import br.com.icorrespondencia.api.domain.validation.View;
import br.com.icorrespondencia.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements CrudController<User, UUID> {

    private final UserService service;

    @JsonView(View.Public.class)
    @GetMapping
    @Override
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.ok(service.index());
    }

    @JsonView(View.Private.class)
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<User> show(@PathVariable UUID id) {
        return ResponseEntity.ok(service.show(id));
    }

    @JsonView(View.Private.class)
    @PostMapping
    @Override
    public ResponseEntity<User> store(
            @RequestBody @Validated(ValidationGroups.Post.class) User user,
            UriComponentsBuilder uriBuilder
    ) {
        User body = service.store(user);

        URI uri = uriBuilder
                        .path("/users/{id}")
                        .buildAndExpand(body.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(body);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> destroy(@PathVariable UUID id) {
        service.destroy(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> update(@RequestBody @Validated(ValidationGroups.Put.class) User entity) {
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
     *         Request} if the user has been previously disabled
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
     *         Request} if the user has been previously enabled
     */
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        boolean isActive = service.activate(id);

        if (!isActive) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validation")
    public ResponseEntity<User> validate(@RequestBody(required = true) Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");

        if(username == null || password == null){
            throw new KeysNotPresentException("Bad body format. Please check documentation.");
        }

        User user = service.userExists(username, password);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}/mailings")
    public ResponseEntity<List<Delivery>> mailings(@PathVariable UUID id){
        return ResponseEntity.ok(service.mailings(id));
    }

    @GetMapping("/{userId}/mailings/{id}")
    public ResponseEntity<Delivery> mailing(@PathVariable("userId") UUID id, @PathVariable("id") Long mailingId){
        return ResponseEntity.ok(service.mailing(id, mailingId));
    }

    @PostMapping("/{userId}/mailings/{id}")
    public ResponseEntity<Void> mailingRead(@PathVariable("userId") UUID id, @PathVariable("id") Long mailingId) {
        boolean status = service.mailingRead(id, mailingId);

        if (!status) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
