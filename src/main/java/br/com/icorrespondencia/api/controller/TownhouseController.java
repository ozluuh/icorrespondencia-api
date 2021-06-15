package br.com.icorrespondencia.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.service.TownhouseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/townhouses")
public class TownhouseController {

    private final TownhouseService service;

    @GetMapping
    public List<Townhouse> index() {
        return service.index().orElse(new ArrayList<>());
    }

    @GetMapping(path = "/{id}")
    public Townhouse show(@PathVariable Long id) {
        Optional<Townhouse> show = service.show(id);

        return show.get();
    }

    @DeleteMapping(path ="/{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        service.destroy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
