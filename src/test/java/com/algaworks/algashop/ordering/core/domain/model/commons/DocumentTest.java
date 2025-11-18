package com.algaworks.algashop.ordering.core.domain.model.commons;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    @DisplayName("Should create Document successfully with a valid value")
    void given_validDocument_whenCreateDocument_shouldCreateSuccessfully() {
        String docString = "123-45-6789";
        Document document = new Document(docString);

        Assertions.assertThat(document.value()).isEqualTo(docString);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create Document with null value")
    void given_nullDocument_whenCreateDocument_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create Document with blank value")
    void given_blankDocument_whenCreateDocument_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document(""));
    }
}
