package br.com.icorrespondencia.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
        return repository.findAllByExcludedIsNull().stream().map(TownhouseMapper.INSTANCE::toTownhouseDTO).collect(Collectors.toList());
    }

    public TownhouseDTO showTownhouseOrThrowBadRequestException(Long id) {
        Townhouse townhouse = repository.getOneByIdAndExcludedAtIsNull(id);
        verifyIfExists(townhouse);
        return TownhouseMapper.INSTANCE.toTownhouseDTO(townhouse);
    }

    public void destroyTownhouseOrThrowBadRequestException(Long id) {
        Townhouse townhouse = TownhouseMapper.INSTANCE.toTownhouse(showTownhouseOrThrowBadRequestException(id));
        repository.setExcludedAndInactiveFor(LocalDateTime.now(), false, townhouse);
    }

    public TownhouseDTO save(TownhouseDTO townhouse) {
        if (townhouse.getId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Id field not be informed");
        }
        Townhouse savedTownhouse = repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
        return TownhouseMapper.INSTANCE.toTownhouseDTO(savedTownhouse);
    }

    public Townhouse updateTownhouseOrThrowBadRequestException(TownhouseDTO townhouse) {
        showTownhouseOrThrowBadRequestException(townhouse.getId());
        return repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    private void verifyIfExists(Townhouse townhouse) {
        if (Objects.isNull(townhouse)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Townhouse not found");
        }
    }
}
