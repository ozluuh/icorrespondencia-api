package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Townhouse;

@Service
public class TownhouseService {

    private List<Townhouse> townhouses = Stream.of(new Townhouse(1L, "Condomínio Fróes"),
            new Townhouse(2L, "Condomínio das Flores"), new Townhouse(3L, "Condomínio Águas Rasas"),
            new Townhouse(4L, "Condomínio Warrick"), new Townhouse(5L, "Condomínio Brooklin"),
            new Townhouse(6L, "Condomínio Kansas"), new Townhouse(7L, "Condomínio Alphaville"),
            new Townhouse(8L, "Condomínio Ritchers"), new Townhouse(9L, "Condomínio AJ Buckley"),
            new Townhouse(10L, "Condomínio Johnson"), new Townhouse(11L, "Condomínio Hills"))
            .collect(Collectors.toList());

    public Optional<List<Townhouse>> index() {
        return Optional.of(townhouses);
    }

    public Optional<Townhouse> show(Long id) {
        return townhouses.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

}
