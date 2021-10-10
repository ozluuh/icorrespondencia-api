package br.com.icorrespondencia.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.icorrespondencia.api.domain.Delivery;
import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.repository.DeliveryRepository;
import br.com.icorrespondencia.api.repository.TownhouseRepository;
import br.com.icorrespondencia.api.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Townhouse service that implements {@link Business} contract
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class TownhouseService implements CrudService<Townhouse, UUID> {

    private final TownhouseRepository repository;

    private final DeliveryRepository deliveryRepo;

    @Override
    public List<Townhouse> index() {
        return repository
                .findAllByExcludedAtIsNull();
    }

    @Override
    public Townhouse show(final UUID id) {
        return repository
                .getOneByIdAndExcludedAtIsNull(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void destroy(final UUID id) {
        show(id);

        repository.excludeAndDeactivateById(id);
    }

    @Override
    public Townhouse store(final Townhouse entity) {
        return repository.save(entity);
    }

    @Override
    public void update(final Townhouse entity) {
        show(entity.getId());

        store(entity);
    }

    public boolean deactivate(final UUID id) {
        show(id);

        return repository.deactivateById(id) != 0;
    }

    public boolean activate(final UUID id) {
        show(id);

        return repository.activateById(id) != 0;
    }

    public Delivery mailings(final Delivery delivery, final UUID id) {
        show(id);

        return deliveryRepo.save(delivery);
    }
}
