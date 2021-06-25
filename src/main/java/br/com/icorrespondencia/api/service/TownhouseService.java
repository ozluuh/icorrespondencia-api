package br.com.icorrespondencia.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.exception.BadRequestException;
import br.com.icorrespondencia.api.exception.UnprocessableEntityException;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService {

    private final TownhouseRepository repository;

    public List<TownhouseDTO> index() {
        return repository
                .findAllByExcludedAtIsNull()
                .stream()
                .map(TownhouseMapper.INSTANCE::toTownhouseDTO)
                .collect(Collectors.toList());
    }

    public TownhouseDTO showTownhouseOrThrowBadRequestException(Long id) {
        return repository
                .findById(id)
                .map(TownhouseMapper.INSTANCE::toTownhouseDTO)
                .orElseThrow(() -> new BadRequestException("Townhouse not found"));
    }

    public void destroyTownhouseOrThrowBadRequestException(Long id) {
        showTownhouseOrThrowBadRequestException(id);

        repository.excludeAndDeactivateFor(LocalDateTime.now(), false, id);
    }

    @Transactional
    public TownhouseDTO storeTownhouseOrThrowUnprocessableEntityException(TownhouseDTO townhouse) {
        verifyCorrectPayload(townhouse);

        Townhouse savedTownhouse = repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));

        return TownhouseMapper.INSTANCE.toTownhouseDTO(savedTownhouse);
    }

    public void updateTownhouseOrThrowBadRequestException(TownhouseDTO townhouse) {
        showTownhouseOrThrowBadRequestException(townhouse.getId());

        repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    private void verifyCorrectPayload(TownhouseDTO townhouse) {
        if (Objects.nonNull(townhouse.getId())) {
            throw new UnprocessableEntityException("Id field not be informed");
        }
    }
}
