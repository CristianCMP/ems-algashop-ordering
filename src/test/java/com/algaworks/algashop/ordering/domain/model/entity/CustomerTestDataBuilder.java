package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder() {
    }

    public static Customer.BrandNewCustumerBuild brandNewCustomer() {
        return Customer.brandNew()
                .fullName(new FullName("Cristian", "Puhl"))
                .birthDate(new BirthDate(LocalDate.of(1998, 1, 29)))
                .email(new Email("cristian.puhl@test.com"))
                .phone(new Phone("123-456-789"))
                .document(new Document("123-45-6789"))
                .promotionNotificationsAllowed(true)
                .address(Address.builder()
                        .state("Bourbon Street")
                        .number("1234")
                        .neighborhood("North Ville")
                        .city("York")
                        .street("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }

    public static Customer.ExistingCustumerBuild existingCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Cristian", "Puhl"))
                .birthDate(new BirthDate(LocalDate.of(1998, 1, 29)))
                .email(new Email("cristian.puhl@test.com"))
                .phone(new Phone("123-456-789"))
                .document(new Document("123-45-6789"))
                .promotionNotificationsAllowed(true)
                .registeredAt(OffsetDateTime.now())
                .archived(false)
                .archivedAt(null)
                .loyaltyPoints(LoyaltyPoints.ZERO)
                .address(Address.builder()
                        .state("Bourbon Street")
                        .number("1234")
                        .neighborhood("North Ville")
                        .city("York")
                        .street("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }

    public static Customer.ExistingCustumerBuild existingAnonymizedCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("000-000-000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(Address.builder()
                        .state("Bourbon Street")
                        .number("1234")
                        .neighborhood("North Ville")
                        .city("York")
                        .street("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }
}
