package com.algaworks.algashop.ordering.core.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    @DisplayName("Should create Email successfully with a valid value")
    void given_validEmail_whenCreateEmail_shouldCreateSuccessfully() {
        String emailValid = "cristia.puhl@test.com";
        Email email = new Email(emailValid);

        Assertions.assertThat(email.value()).isEqualTo(emailValid);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create Email with null value")
    void given_nullEmail_whenCreateEmail_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Email(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create Email with blank value")
    void given_blankEmail_whenCreateEmail_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email(""));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create Email with invalid value")
    void given_invalidEmail_whenCreateEmail_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("invalid"));
    }
}