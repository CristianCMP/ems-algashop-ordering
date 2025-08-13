package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.ERROR_ORDER_CANNOT_BE_EDITED;

public class OrderCannotBeEditedException extends DomainExeption {
    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }
}
