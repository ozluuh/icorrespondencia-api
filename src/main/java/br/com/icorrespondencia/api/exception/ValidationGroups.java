package br.com.icorrespondencia.api.exception;

import javax.validation.groups.Default;

/**
 * Contract to use in payload validation
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
public interface ValidationGroups {
    /**
     * Contract to HTTP PUT verb
     */
    interface Put extends Default {
    }

    /**
     * Contract to HTTP POST verb
     */
    interface Post extends Default {
    }
}
