package com.algaworks.algashop.ordering.domain.model.exeption;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.*;

public class OrderCannotBePlacedException extends DomainExeption {

    private OrderCannotBePlacedException(String message) {
        super(message);
    }

    public static OrderCannotBePlacedException noItems(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId));
    }

    public static OrderCannotBePlacedException noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING, orderId));
    }

    public static OrderCannotBePlacedException noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING, orderId));
    }

    public static OrderCannotBePlacedException noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId));
    }


}
