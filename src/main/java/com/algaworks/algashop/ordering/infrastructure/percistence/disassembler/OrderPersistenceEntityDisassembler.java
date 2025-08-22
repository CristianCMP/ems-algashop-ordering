package com.algaworks.algashop.ordering.infrastructure.percistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderItem;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.percistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity){
        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .shipping(toShippingValueObject(persistenceEntity.getShipping()))
                .billing(toBillingValueObject(persistenceEntity.getBilling()))
                .items(toOrderItem(persistenceEntity.getItems()))
                .version(persistenceEntity.getVersion())
                .build();
    }

    public static Billing toBillingValueObject(BillingEmbeddable billing) {
        if (billing == null) return null;
        return Billing.builder()
                .phone(new Phone(billing.getPhone()))
                .email(new Email(billing.getEmail()))
                .fullName(new FullName(billing.getFirstName(), billing.getLastName()))
                .address(toAddressValueObject(billing.getAddress()))
                .build();
    }

    public static Address toAddressValueObject(AddressEmbeddable address) {
        if (address == null) return null;
        return Address.builder()
                .city(address.getCity())
                .complement(address.getComplement())
                .street(address.getStreet())
                .state(address.getState())
                .neighborhood(address.getNeighborhood())
                .number(address.getNumber())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }

    public static Shipping toShippingValueObject(ShippingEmbeddable shipping) {
        if (shipping == null) return null;
        return Shipping.builder()
                .expectedDate(shipping.getExpectedDate())
                .cost(new Money(shipping.getCost()))
                .address(toAddressValueObject(shipping.getAddress()))
                .recipient(toRecipientValueObject(shipping.getRecipient()))
                .build();
    }

    private static Recipient toRecipientValueObject(RecipientEmbeddable recipient) {
        if (recipient == null) return null;
        return Recipient.builder()
                .fullName(new FullName(recipient.getFirstName(), recipient.getLastName()))
                .document(new Document(recipient.getDocument()))
                .phone(new Phone(recipient.getPhone()))
                .build();
    }

    private Set<OrderItem> toOrderItem(Set<OrderItemPersistenceEntity> items) {
        return items
                .stream()
                .map(i -> OrderItem.brandNew()
                        .orderId(new OrderId(i.getOrderId()))
                        .quantity(new Quantity(i.getQuantity()))
                        .product(new Product(
                                new ProductId(i.getProductId()),
                                new ProductName(i.getProductName()),
                                new Money(i.getPrice()),
                                true
                        ))
                        .build())
                .collect(Collectors.toSet());
    }
}
