package com.algaworks.algashop.ordering.domain.model.exeption;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.*;

public class OrderCannotBePlacedExeption extends DomainExeption {

    private OrderCannotBePlacedExeption(String message) {
        super(message);
    }

    public static OrderCannotBePlacedExeption noItems(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId));
    }

    public static OrderCannotBePlacedExeption noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING, orderId));
    }

    public static OrderCannotBePlacedExeption noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING, orderId));
    }

    public static OrderCannotBePlacedExeption noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedExeption(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId));
    }


}
