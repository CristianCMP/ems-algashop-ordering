package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMenssages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGE;

public class OrderStatusCannotBeChangeException extends DomainException {

    public OrderStatusCannotBeChangeException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGE, id, status, newStatus));
    }
}
