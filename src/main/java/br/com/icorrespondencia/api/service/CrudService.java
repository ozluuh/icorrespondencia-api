package br.com.icorrespondencia.api.service;

/**
 * Business rules layer contract for the
 * {@link br.com.icorrespondencia.api.controller.CrudController CrudController} contract
 *
 * @author Luís Paulino
 * @since 0.2
 * @version 1.0
 */
public interface CrudService<E, K> extends Business<E, K> {

    /**
     * Business rules to store entity data
     *
     * @param entity to be stored
     * @return stored entity with id
     */
    E store(E entity);

    /**
     * Business rules to update entity data
     *
     * @param entity to be updated
     */
    void update(E entity);

    /**
     * Business rules to destroy entity
     *
     * @param id to destroy
     */
    void destroy(K id);
}
