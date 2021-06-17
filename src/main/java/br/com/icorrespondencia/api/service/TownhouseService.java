package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService {

    private final TownhouseRepository repository;

    public List<TownhouseDTO> index() {
        return repository.findAll().stream().map(TownhouseMapper.INSTANCE::toTownhouseDTO).collect(Collectors.toList());
    }

    public TownhouseDTO showTownhouseOrThrowBadRequestException(Long id) {
        Optional<Townhouse> townhouse = findInstanceInDatabase(id);
        if (!townhouse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Townhouse not found");
        }
        return TownhouseMapper.INSTANCE.toTownhouseDTO(townhouse.get());
    }

    public void destroyTownhouseOrThrowBadRequestException(Long id) {
        Optional<Townhouse> savedTownhouse = findInstanceInDatabase(id);
        if (!savedTownhouse.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Townhouse not found");
        }
        repository.delete(savedTownhouse.get());
    }

    public Townhouse save(TownhouseDTO townhouse) {
        if (townhouse.getId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Id field not be informed");
        }
        return repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    public Townhouse updateTownhouseOrThrowBadRequestException(TownhouseDTO townhouse) {
        showTownhouseOrThrowBadRequestException(townhouse.getId());
        return repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    private Optional<Townhouse> findInstanceInDatabase(Long id) {
        return repository.findById(id);
    }

    private void verifyIfExists(Townhouse townhouse){
        if(Objects.isNull(townhouse)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Townhouse not found");
        }
}
