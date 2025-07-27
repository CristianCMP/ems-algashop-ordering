package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.CustomerArquivedExeption;
import com.algaworks.algashop.ordering.domain.utitly.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
                .isThrownBy(() -> customer.changeEmail("invalid"));
    }

    @Test
    @DisplayName("Should anonymize Customer data when an unarchived Customer is archived")
    void given_unarquivedCustomer_whenArquived_shouldAnonymize() {
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

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo("Anonymous"),
                c -> assertThat(c.email()).isNotEqualTo("cristian.puhl@test.com"),
                c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document()).isEqualTo("000-00-0000"),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllower()).isFalse()
        );
    }

    @Test
    @DisplayName("Should throw CustomerArquivedExeption when trying to update an already archived Customer")
    void given_arquivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonymous",
                null,
                "anonymous@anonymous.com",
                "000-000-000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                10
        );

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::enablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::disablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changeEmail("email@test.com"));

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changeName("Cristian Puhl"));

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changePhone("123-456-789"));
    }

    @Test
    @DisplayName("Should sum value when adding loyalty value to a new Customer")
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
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

        customer.addLoyaltyPoints(10);
        customer.addLoyaltyPoints(20);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(30);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding invalid loyalty value to a new Customer")
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerationExeption() {
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
                .isThrownBy(() -> customer.addLoyaltyPoints(0));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(-10));
    }
}