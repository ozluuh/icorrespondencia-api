package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.InvalidPayloadException;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownhouseService implements Business<TownhouseDTO, Long> {

    private final TownhouseRepository repository;

    @Override
    public List<TownhouseDTO> index() {
        return repository
                .findAllByExcludedAtIsNull()
                .stream()
                .map(TownhouseMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TownhouseDTO show(Long id) {
        return repository
                .getOneByIdAndExcludedAtIsNull(id)
                .map(TownhouseMapper.INSTANCE::toDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void destroy(Long id) {
        show(id);

        repository.excludeAndDeactivateById(id);
    }

    public TownhouseDTO store(TownhouseDTO entity) {
        verifyCorrectPayload(entity);

        Townhouse storedTownhouse = repository.save(TownhouseMapper.INSTANCE.toDomain(entity));

        return TownhouseMapper.INSTANCE.toDTO(storedTownhouse);
    }

    public void update(TownhouseDTO entity) {
        show(entity.getId());

        repository.save(TownhouseMapper.INSTANCE.toDomain(entity));
    }

    private void verifyCorrectPayload(TownhouseDTO entity) {
        if(Objects.nonNull(entity.getId())) throw new InvalidPayloadException("id field should not be informed");
    }
}
