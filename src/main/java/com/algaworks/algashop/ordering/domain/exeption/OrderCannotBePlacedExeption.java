package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.*;

public class OrderCannotBePlacedExeption extends DomainExeption {

    private OrderCannotBePlacedExeption(String message) {
        super(message);
    }

    public static OrderCannotBePlacedExeption noItems(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId));
    }

    public static OrderCannotBePlacedExeption noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, orderId));
    }

    public static OrderCannotBePlacedExeption noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, orderId));
    }

    public static OrderCannotBePlacedExeption noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId));
    }


}
