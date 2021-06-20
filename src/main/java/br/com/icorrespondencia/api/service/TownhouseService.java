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
                .findAllByExcludedIsNull()
                .stream()
                .map(TownhouseMapper.INSTANCE::toTownhouseDTO)
                .collect(Collectors.toList());
    }

    public TownhouseDTO showTownhouseOrThrowBadRequestException(Long id) {
        Townhouse townhouse = repository.getOneByIdAndExcludedAtIsNull(id);
        verifyIfExists(townhouse);
        return TownhouseMapper.INSTANCE.toTownhouseDTO(townhouse);
    }

    public void destroyTownhouseOrThrowBadRequestException(Long id) {
        Townhouse townhouse = TownhouseMapper.INSTANCE.toTownhouse(showTownhouseOrThrowBadRequestException(id));

        townhouse.setActive(false);
        townhouse.setExcludedAt(LocalDateTime.now());

        repository.save(townhouse);
    }

    @Transactional
    public TownhouseDTO save(TownhouseDTO townhouse) {
        verifyCorrectPayload(townhouse);
        Townhouse savedTownhouse = repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
        return TownhouseMapper.INSTANCE.toTownhouseDTO(savedTownhouse);
    }

    public Townhouse updateTownhouseOrThrowBadRequestException(TownhouseDTO townhouse) {
        showTownhouseOrThrowBadRequestException(townhouse.getId());
        return repository.save(TownhouseMapper.INSTANCE.toTownhouse(townhouse));
    }

    private void verifyIfExists(Townhouse townhouse) {
        if (Objects.isNull(townhouse)) {
            throw new BadRequestException("Townhouse not found");
        }
    }

    private void verifyCorrectPayload(TownhouseDTO townhouse) {
        if (Objects.nonNull(townhouse.getId())) {
            throw new UnprocessableEntityException("Id field not be informed");
        }
    }
}
