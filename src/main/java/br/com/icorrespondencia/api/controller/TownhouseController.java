package br.com.icorrespondencia.api.controller;

import java.util.List;

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
        return service.index();
    }

    @GetMapping(path = "/{id}")
    public Townhouse show(@PathVariable Long id) {
        return service.show(id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        service.destroy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path ="/{id}")
    public ResponseEntity<Townhouse> update(@RequestBody Townhouse townhouse, @PathVariable Long id){
        Townhouse entity = show(id);

        if(entity != null) {
            townhouse.setId(id);
            service.update(townhouse);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Townhouse> store(@RequestBody Townhouse townhouse){
        return ResponseEntity.ok(service.store(townhouse));
    }
}
