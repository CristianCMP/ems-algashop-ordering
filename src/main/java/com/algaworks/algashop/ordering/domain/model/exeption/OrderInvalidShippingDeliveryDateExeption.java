package com.algaworks.algashop.ordering.domain.model.exeption;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.ERROR_ORDER_DELIVERY_DATE_CANNOT_IN_THE_PAST;

public class OrderInvalidShippingDeliveryDateExeption extends DomainExeption {

    public OrderInvalidShippingDeliveryDateExeption(OrderId id) {
        super(String.format(ERROR_ORDER_DELIVERY_DATE_CANNOT_IN_THE_PAST, id));
    }
}