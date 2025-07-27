package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.utitly.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating Customer with invalid email")
    void give_invalidEmail_whenTryCreateCustomer_shouldGenerateExeption() {
        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new Customer(
                            IdGenerator.generateTimeBasedUUID(),
                            "Cristian Puhl",
                            LocalDate.of(1998, 1, 29),
                            "invalid",
                            "123-456-789",
                            "123-45-6789",
                            true,
                            OffsetDateTime.now()
                    );
                });
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating Customer email with invalid value")
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Cristian Puhl",
                LocalDate.of(1998, 1, 29),
                "cristian.puhl@test.com",
                "123-456-789",
                "123-45-6789",
                true,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("invalid");
                });
    }
}