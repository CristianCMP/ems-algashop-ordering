package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.CustomerArquivedExeption;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating Customer with invalid email")
    void give_invalidEmail_whenTryCreateCustomer_shouldGenerateExeption() {
        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> CustomerTestDataBuilde.brandNewCustomer()
                        .email(new Email("invalid")).build());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating Customer email with invalid value")
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilde.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid")));
    }

    @Test
    @DisplayName("Should anonymize Customer data when an unarchived Customer is archived")
    void given_unarquivedCustomer_whenArquived_shouldAnonymize() {
        Customer customer = CustomerTestDataBuilde.existingCustomer().build();

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo(new Email("cristian.puhl@test.com")),
                c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllower()).isFalse(),
                c -> assertThat(c.address()).isEqualTo(
                        Address.builder()
                                .state("Bourbon Street")
                                .number("Anonymized")
                                .neighborhood("North Ville")
                                .city("York")
                                .street("South California")
                                .zipCode(new ZipCode("12345"))
                                .complement(null)
                                .build()
                ));
    }

    @Test
    @DisplayName("Should throw CustomerArquivedExeption when trying to update an already archived Customer")
    void given_arquivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilde.existingAnonymizedCustomer().build();

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::enablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(customer::disablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changeEmail(new Email("email@test.com")));

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changeName(new FullName("Cristian", "Puhl")));

        Assertions.assertThatExceptionOfType(CustomerArquivedExeption.class)
                .isThrownBy(() -> customer.changePhone(new Phone("123-456-789")));
    }

    @Test
    @DisplayName("Should sum value when adding loyalty value to a new Customer")
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = CustomerTestDataBuilde.brandNewCustomer().build();

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding invalid loyalty value to a new Customer")
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerationExeption() {
        Customer customer = CustomerTestDataBuilde.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(LoyaltyPoints.ZERO));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }
}