package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    @DisplayName("Should create Phone successfully with a valid value")
    void given_validPhone_whenCreatePhone_shouldCreateSuccessfully() {
        String phoneValid = "123-456-789";
        Phone phone = new Phone(phoneValid);

        Assertions.assertThat(phone.value()).isEqualTo(phoneValid);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create Phone with null value")
    void given_nullPhone_whenCreatePhone_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Phone(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create Phone with blank value")
    void given_blankPhone_whenCreatePhone_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone(""));
    }
}