package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.utitly.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void testingCustomer() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
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