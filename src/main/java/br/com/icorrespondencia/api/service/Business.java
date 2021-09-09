package br.com.icorrespondencia.api.service;

import java.util.List;

/**
 * Business rules layer contract for the
 * {@link br.com.icorrespondencia.api.controller.Endpoint Endpoint} contract
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 1.0
 */
public interface Business<E, K> {

    /**
     * Business layer to list all entities
     *
     * @return entities list or empty list
     */
    List<E> index();

    /**
     * Business layer to show one entity
     *
     * @param id to be searched
     * @return entity instance
     * @throws ResourceNotFoundException in case of entity not found
     */
    E show(K id);
}
