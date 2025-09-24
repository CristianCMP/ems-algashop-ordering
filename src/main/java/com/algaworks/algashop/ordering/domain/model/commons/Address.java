package com.algaworks.algashop.ordering.domain.model.commons;

import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.FieldValidations.*;

@Builder(toBuilder = true)
public record Address(
        String street,
        String complement,
        String number,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {
    public Address {
        requiresNonBlank(street);
        requiresNonBlank(neighborhood);
        requiresNonBlank(city);
        requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}
