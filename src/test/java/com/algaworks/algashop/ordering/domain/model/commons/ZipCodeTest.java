package com.algaworks.algashop.ordering.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZipCodeTest {

    @Test
    @DisplayName("Should create ZipCode successfully with a valid value")
    void given_validZipCode_whenCreateZipCode_shouldCreateSuccessfully() {
        String validZipCode = "12345";
        ZipCode zipCode = new ZipCode(validZipCode);

        Assertions.assertThat(zipCode.value()).isEqualTo(validZipCode);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create ZipCode with null value")
    void given_nullZipCode_whenCreateZipCode_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ZipCode(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create ZipCode with blank value")
    void given_blankZipCode_whenCreateZipCode_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode(""));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create ZipCode with less than 5 characters")
    void given_shortZipCode_whenCreateZipCode_shouldThrowException() {
        String shortZipCode = "1234";
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode(shortZipCode));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create ZipCode with more than 5 characters")
    void given_longZipCode_whenCreateZipCode_shouldThrowException() {
        String longZipCode = "123456";
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode(longZipCode));
    }
}