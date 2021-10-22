package br.com.icorrespondencia.api.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeliveryType {
    CONTA_LUZ("Conta Luz"),
    CONTA_AGUA("Conta Água"),
    ENCOMENDA("Encomenda"),
    OUTROS("Outros");

    @Getter(onMethod = @__(@JsonValue))
    private final String label;

    @Override
    public String toString() {
        return this.label;
    }
}
