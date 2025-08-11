package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGE;

public class OrderStatusCannotBeChangeExeption extends DomainExeption {

    public OrderStatusCannotBeChangeExeption(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGE, id, status, newStatus));
    }
}
