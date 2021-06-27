package br.com.icorrespondencia.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.service.TownhouseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/townhouses")
public class TownhouseController implements Endpoint<TownhouseDTO, Long> {

    private final TownhouseService service;

    @Override
    public ResponseEntity<List<TownhouseDTO>> index() {
        return ResponseEntity.ok(service.index());
    }

    @Override
    public ResponseEntity<TownhouseDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @Override
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroy(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<TownhouseDTO> store(@RequestBody @Valid TownhouseDTO entity) {
        return new ResponseEntity<>(service.store(entity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> update(@RequestBody @Valid TownhouseDTO entity) {
        service.update(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
