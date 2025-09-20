package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity.CustomerPersistenceEntityBuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerPersistenceTestDataBuilder {

    public static CustomerPersistenceEntityBuilder aCustomer() {
        return CustomerPersistenceEntity.builder();
    }

    public static CustomerPersistenceEntityBuilder existingCustomer() {
        return CustomerPersistenceEntity.builder()
                .id(new CustomerId().value())
                .firstName("John")
                .lastName("Smith")
                .birthDate(LocalDate.now())
                .email("teste@gmail.com")
                .phone("123456789")
                .document("Document")
                .promotionNotificationsAllowed(true)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(2)
                .address(OrderPersistenceEntityAssembler
                        .toAddressEmbeddable(Address.builder()
                                .street("Bourbon Street")
                                .number("1134")
                                .neighborhood("North Ville")
                                .city("York")
                                .state("South California")
                                .zipCode(new ZipCode("12345"))
                                .complement("Apt. 114")
                                .build()));
    }

}