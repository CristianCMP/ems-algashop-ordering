package com.algaworks.algashop.ordering.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityTest {
    @Test
    void shouldGenerateWithValue() {
        Quantity quantity = new Quantity(10);

        Assertions.assertThat(quantity.value()).isEqualTo(10);
    }

    @Test
    void shouldAddValue() {
        Quantity quantity = new Quantity(10);

        Quantity quantityUpdated = quantity.add(new Quantity(5));

        Assertions.assertThat(quantityUpdated.value()).isEqualTo(15);
    }

    @Test
    void shouldAddNegativeValue() {
        Quantity quantity = new Quantity(10);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> quantity.add(new Quantity(-10)));

        Assertions.assertThat(quantity.value()).isEqualTo(10);
    }

    @Test
    void shouldNotAddZeroValue() {
        Quantity quantity = new Quantity(10);

        Quantity quantityAdded = quantity.add(Quantity.ZERO);

        Assertions.assertThat(quantityAdded.value()).isEqualTo(10);
    }
}