package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BillingTest {

    private FullName validFullName() {
        return new FullName("Cristian", "Puhl");
    }

    private Document validDocument() {
        return new Document("123-45-6789");
    }

    private Phone validPhone() {
        return new Phone("123-456-789");
    }

    private Email validEmail() {
        return new Email("cristian@teste.com");
    }

    private Address validAddress() {
        return new Address(
                "Main Street",
                "Apt 101",
                "100",
                "Downtown",
                "New York",
                "NY",
                new ZipCode("12345")
        );
    }

    @Test
    @DisplayName("Should create BillingInfo successfully with all valid values")
    void given_validBillingInfo_whenCreate_shouldCreateSuccessfully() {
        Billing billing = new Billing(
                validFullName(),
                validDocument(),
                validPhone(),
                validEmail(),
                validAddress()
        );

        Assertions.assertThat(billing.fullName()).isEqualTo(validFullName());
        Assertions.assertThat(billing.document()).isEqualTo(validDocument());
        Assertions.assertThat(billing.phone()).isEqualTo(validPhone());
        Assertions.assertThat(billing.address()).isEqualTo(validAddress());
    }

    @Test
    @DisplayName("Should throw NullPointerException when FullName is null")
    void given_nullFullName_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Billing(
                        null,
                        validDocument(),
                        validPhone(),
                        validEmail(),
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Document is null")
    void given_nullDocument_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Billing(
                        validFullName(),
                        null,
                        validPhone(),
                        validEmail(),
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Phone is null")
    void given_nullPhone_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Billing(
                        validFullName(),
                        validDocument(),
                        null,
                        validEmail(),
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Email is null")
    void given_nullEmail_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Billing(
                        validFullName(),
                        validDocument(),
                        validPhone(),
                        null,
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Address is null")
    void given_nullAddress_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Billing(
                        validFullName(),
                        validDocument(),
                        validPhone(),
                        validEmail(),
                        null
                ));
    }
}
