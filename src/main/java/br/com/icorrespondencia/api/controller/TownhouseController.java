package br.com.icorrespondencia.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.service.TownhouseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/townhouses")
public class TownhouseController {

    private final TownhouseService service;

    @GetMapping
    public ResponseEntity<List<TownhouseDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TownhouseDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.showOrThrowBadRequestException(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroyOrThrowBadRequestException(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid TownhouseDTO townhouse) {
        service.updateOrThrowBadRequestException(townhouse);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<TownhouseDTO> store(@RequestBody @Valid TownhouseDTO townhouse) {
        TownhouseDTO storedTownhouse = service.storeOrThrowUnprocessableEntityException(townhouse);

        return new ResponseEntity<>(storedTownhouse, HttpStatus.CREATED);
    }
}
