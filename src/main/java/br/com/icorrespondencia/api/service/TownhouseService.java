package br.com.icorrespondencia.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService {

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

    public Townhouse saveOrUpdate(Townhouse townhouse) {
        return repository.save(townhouse);
    }

}
