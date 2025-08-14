package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("Should create Address successfully with all valid values")
    void given_validAddressData_whenCreateAddress_shouldCreateSuccessfully() {
        ZipCode zipCode = new ZipCode("12345");
        Address address = new Address(
                "Main Street",
                "Apt 101",
                "100",
                "Downtown",
                "New York",
                "NY",
                zipCode
        );

        Assertions.assertThat(address.street()).isEqualTo("Main Street");
        Assertions.assertThat(address.complement()).isEqualTo("Apt 101");
        Assertions.assertThat(address.number()).isEqualTo("100");
        Assertions.assertThat(address.neighborhood()).isEqualTo("Downtown");
        Assertions.assertThat(address.city()).isEqualTo("New York");
        Assertions.assertThat(address.state()).isEqualTo("NY");
        Assertions.assertThat(address.zipCode()).isEqualTo(zipCode);
    }

    @Test
    @DisplayName("Should throw NullPointerException when trying to create Address with null ZipCode")
    void given_nullZipCode_whenCreateAddress_shouldThrowException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Address(
                        "Main Street",
                        "Apt 101",
                        "100",
                        "Downtown",
                        "New York",
                        "NY",
                        null
                ));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when street is blank")
    void given_blankStreet_whenCreateAddress_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Address(
                        "",
                        "Apt 101",
                        "100",
                        "Downtown",
                        "New York",
                        "NY",
                        new ZipCode("12345")
                ));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when neighborhood is blank")
    void given_blankNeighborhood_whenCreateAddress_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Address(
                        "Main Street",
                        "Apt 101",
                        "100",
                        "",
                        "New York",
                        "NY",
                        new ZipCode("12345")
                ));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when city is blank")
    void given_blankCity_whenCreateAddress_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Address(
                        "Main Street",
                        "Apt 101",
                        "100",
                        "Downtown",
                        "",
                        "NY",
                        new ZipCode("12345")
                ));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when state is blank")
    void given_blankState_whenCreateAddress_shouldThrowException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Address(
                        "Main Street",
                        "Apt 101",
                        "100",
                        "Downtown",
                        "New York",
                        "",
                        new ZipCode("12345")
                ));
    }
}
