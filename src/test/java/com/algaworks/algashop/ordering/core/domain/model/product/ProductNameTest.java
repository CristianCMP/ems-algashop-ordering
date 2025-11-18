package com.algaworks.algashop.ordering.core.domain.model.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {
    @Test
    @DisplayName("Should create ProductName successfully with a valid value")
    void given_validProductName_whenCreateProductName_shouldCreateSuccessfully() {
        String docString = "Product";
        ProductName productName = new ProductName(docString);

        Assertions.assertThat(productName.value()).isEqualTo(docString);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create ProductName with null value")
    void given_nullProductName_whenCreateProductName_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ProductName(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create ProductName with blank value")
    void given_blankProductName_whenCreateProductName_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(""));
    }
}