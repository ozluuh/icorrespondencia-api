package br.com.icorrespondencia.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
}
