package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService {

    private List<Townhouse> townhouses = Stream.of(new Townhouse(1L, "Condomínio Fróes"),
            new Townhouse(2L, "Condomínio das Flores"), new Townhouse(3L, "Condomínio Águas Rasas"),
            new Townhouse(4L, "Condomínio Warrick"), new Townhouse(5L, "Condomínio Brooklin"),
            new Townhouse(6L, "Condomínio Kansas"), new Townhouse(7L, "Condomínio Alphaville"),
            new Townhouse(8L, "Condomínio Ritchers"), new Townhouse(9L, "Condomínio AJ Buckley"),
            new Townhouse(10L, "Condomínio Johnson"), new Townhouse(11L, "Condomínio Hills"))
            .collect(Collectors.toList());

    private final TownhouseRepository repository;

    public List<Townhouse> index() {
        return repository.findAll();
    }

    public Optional<Townhouse> show(Long id) {
        return townhouses.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public void destroy(Long id) {
        townhouses = townhouses.stream().filter(item -> !item.getId().equals(id)).collect(Collectors.toList());
    }

    public void update(Townhouse townhouse) {
        destroy(townhouse.getId());
        townhouses.add(townhouse);
    }

    public Townhouse store(Townhouse townhouse) {
        int lastId = townhouses.size();
        townhouse.setId((long) ++lastId);
        townhouses.add(townhouse);

        return townhouse;
    }

}
