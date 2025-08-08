package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BillingInfoTest {

    private FullName validFullName() {
        return new FullName("Cristian", "Puhl");
    }

    private Document validDocument() {
        return new Document("123-45-6789");
    }

    private Phone validPhone() {
        return new Phone("123-456-789");
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
        BillingInfo billingInfo = new BillingInfo(
                validFullName(),
                validDocument(),
                validPhone(),
                validAddress()
        );

        Assertions.assertThat(billingInfo.fullName()).isEqualTo(validFullName());
        Assertions.assertThat(billingInfo.document()).isEqualTo(validDocument());
        Assertions.assertThat(billingInfo.phone()).isEqualTo(validPhone());
        Assertions.assertThat(billingInfo.address()).isEqualTo(validAddress());
    }

    @Test
    @DisplayName("Should throw NullPointerException when FullName is null")
    void given_nullFullName_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BillingInfo(
                        null,
                        validDocument(),
                        validPhone(),
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Document is null")
    void given_nullDocument_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BillingInfo(
                        validFullName(),
                        null,
                        validPhone(),
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Phone is null")
    void given_nullPhone_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BillingInfo(
                        validFullName(),
                        validDocument(),
                        null,
                        validAddress()
                ));
    }

    @Test
    @DisplayName("Should throw NullPointerException when Address is null")
    void given_nullAddress_whenCreate_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BillingInfo(
                        validFullName(),
                        validDocument(),
                        validPhone(),
                        null
                ));
    }
}
