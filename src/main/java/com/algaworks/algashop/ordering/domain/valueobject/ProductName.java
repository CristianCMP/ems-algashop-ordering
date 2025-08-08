package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record ProductName(String value) {

    public ProductName {
        Objects.requireNonNull(value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}