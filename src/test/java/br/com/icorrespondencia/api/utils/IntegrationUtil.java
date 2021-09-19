package br.com.icorrespondencia.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class IntegrationUtil {

    public static final String asJsonString(final Object entity) {
        try {
            return new ObjectMapper().writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
