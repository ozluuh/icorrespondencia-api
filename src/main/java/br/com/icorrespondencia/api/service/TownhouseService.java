package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Townhouse show(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Townhouse not found"));
    }

    public void destroy(Long id) {
        repository.delete(show(id));
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
