package com.algaworks.algashop.ordering.infrastructure.percistence.entity;

import com.algaworks.algashop.ordering.domain.model.utitly.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderPersistenceTestDataBuilder {

    private OrderPersistenceTestDataBuilder() {
    }

    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now());
    }
}
