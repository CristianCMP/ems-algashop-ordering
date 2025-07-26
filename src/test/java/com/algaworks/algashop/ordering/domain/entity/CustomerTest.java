package com.algaworks.algashop.ordering.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

class CustomerTest {

    @Test
    void testingCustomer() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "Cristian Puhl",
                LocalDate.of(1998, 1, 29),
                "cristian.puhl@teste.com",
                "123-456-789",
                "123-45-6789",
                true,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(10);
    }
}