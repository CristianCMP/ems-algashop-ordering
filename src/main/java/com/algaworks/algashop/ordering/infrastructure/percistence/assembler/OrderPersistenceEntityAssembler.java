package com.algaworks.algashop.ordering.infrastructure.percistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.valueobject.Recipient;
import com.algaworks.algashop.ordering.domain.model.valueobject.Shipping;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order) {
        orderPersistenceEntity.setId(order.id().value().toLong());
        orderPersistenceEntity.setCustomerId(order.customerId().value());
        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setStatus(order.status().name());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod().name());
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCanceledAt(order.canceledAt());
        orderPersistenceEntity.setReadyAt(order.readyAt());
        orderPersistenceEntity.setBilling(convertBillingToEmbeddable(order.billing()));
        orderPersistenceEntity.setShipping(convertShippingToEmbeddable(order.shipping()));
        orderPersistenceEntity.setVersion(order.version()); //Here it is optional

        return orderPersistenceEntity;
    }

    public static BillingEmbeddable convertBillingToEmbeddable(Billing billing) {
        if (billing == null) return null;
        return BillingEmbeddable.builder()
                .firstName(billing.fullName().firstName())
                .lastName(billing.fullName().lastName())
                .phone(billing.phone().value())
                .document(billing.document().value())
                .address(convertAddressToEmbeddable(billing.address()))
                .build();
    }

    public static AddressEmbeddable convertAddressToEmbeddable(Address address) {
        if (address == null) return null;
        return AddressEmbeddable.builder()
                .city(address.city())
                .complement(address.complement())
                .street(address.street())
                .state(address.state())
                .neighborhood(address.neighborhood())
                .number(address.number())
                .zipCode(address.zipCode().value())
                .build();
    }

    public static ShippingEmbeddable convertShippingToEmbeddable(Shipping shipping) {
        if (shipping == null) return null;
        return ShippingEmbeddable.builder()
                .expectedDate(shipping.expectedDate())
                .cost(shipping.cost().value())
                .address(convertAddressToEmbeddable(shipping.address()))
                .recipient(convertRecipientToEmbeddable(shipping.recipient()))
                .build();
    }

    private static RecipientEmbeddable convertRecipientToEmbeddable(Recipient recipient) {
        return RecipientEmbeddable.builder()
                .firstName(recipient.fullName().firstName())
                .lastName(recipient.fullName().lastName())
                .document(recipient.document().value())
                .phone(recipient.phone().value())
                .build();
    }
}
