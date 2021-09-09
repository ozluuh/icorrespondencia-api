package br.com.icorrespondencia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Generated;

@RestController
@RequestMapping(value = { "/", "/tests", "/test" })
public class SampleController {

    @Generated
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Stranger, stranger! Happy coding!");
    }
}
