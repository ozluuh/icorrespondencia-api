package br.com.icorrespondencia.api.service;

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

    public TownhouseDTO showOrThrowBadRequestException(Long id) {
        return repository
                .getOneByIdAndExcludedAtIsNull(id)
                .map(TownhouseMapper.INSTANCE::toTownhouseDTO)
                .orElseThrow(() -> new BadRequestException("Townhouse not found"));
    }

    public void destroyOrThrowBadRequestException(Long id) {
        showOrThrowBadRequestException(id);

        repository.excludeAndDeactivateById(id);
    }

    @Transactional
    public TownhouseDTO storeOrThrowUnprocessableEntityException(TownhouseDTO townhouse) {
        verifyCorrectPayload(townhouse);

        Townhouse savedTownhouse = repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));

        return TownhouseMapper.INSTANCE.toTownhouseDTO(savedTownhouse);
    }

    public void updateOrThrowBadRequestException(TownhouseDTO townhouse) {
        showOrThrowBadRequestException(townhouse.getId());

        repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    private void verifyCorrectPayload(TownhouseDTO townhouse) {
        if (Objects.nonNull(townhouse.getId())) {
            throw new UnprocessableEntityException("Id field not be informed");
        }
    }
}
