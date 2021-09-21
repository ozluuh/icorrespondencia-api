package br.com.icorrespondencia.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Townhouse service that implements {@link Business} contract
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@RequiredArgsConstructor
@Service
public class TownhouseService implements CrudService<Townhouse, Long> {

    private final TownhouseRepository repository;

    @Override
    public List<Townhouse> index() {
        return repository
                .findAllByExcludedAtIsNull();
    }

    @Override
    public Townhouse show(final Long id) {
        return repository
                .getOneByIdAndExcludedAtIsNull(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void destroy(final Long id) {
        show(id);

        repository.excludeAndDeactivateById(id);
    }

    @Override
    public Townhouse store(final Townhouse entity) {
        return repository.save(entity);
    }

    @Override
    public void update(Townhouse entity) {
        show(entity.getId());

        store(entity);
    }
}
