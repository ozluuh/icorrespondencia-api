package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService implements Business<TownhouseDTO, Long> {

    private final TownhouseRepository repository;

    private final TownhouseMapper mapper;

    @Override
    public List<TownhouseDTO> index() {
        return repository
                .findAllByExcludedAtIsNull()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TownhouseDTO show(final Long id) {
        return repository
                .getOneByIdAndExcludedAtIsNull(id)
                .map(mapper::toDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void destroy(final Long id) {
        show(id);

        repository.excludeAndDeactivateById(id);
    }

    public TownhouseDTO store(final TownhouseDTO entity) {
        final Townhouse storedTownhouse = repository.save(mapper.toDomain(entity));

        return mapper.toDTO(storedTownhouse);
    }

    public void update(final TownhouseDTO entity) {
        show(entity.getId());

        store(entity);
    }
}
