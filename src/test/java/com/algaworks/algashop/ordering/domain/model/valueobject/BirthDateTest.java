package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    @DisplayName("Should create BirthDate successfully with a valid date")
    void given_validDate_whenCreateBirthDate_shouldCreateSuccessfully() {
        LocalDate dateValid = LocalDate.of(1998, 1, 29);
        BirthDate birthDate = new BirthDate(dateValid);
        Assertions.assertThat(birthDate.value()).isEqualTo(dateValid);
    }

    @Test
    @DisplayName("Should calculate correct age in years when given a valid birth date")
    void given_validBirthDate_whenCalculateAge_shouldReturnCorrectAge() {
        LocalDate dateBirth = LocalDate.now().minusYears(27);
        BirthDate birthDate = new BirthDate(dateBirth);

        Assertions.assertThat(birthDate.age()).isEqualTo(27);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying to create BirthDate with a future date")
    void given_futureDate_whenCreateBirthDate_shouldThrowException() {
        LocalDate dateFutute = LocalDate.now().plusDays(1);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(dateFutute));
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create BirthDate with a null value")
    void given_nullDate_whenCreateBirthDate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BirthDate(null));
    }
}