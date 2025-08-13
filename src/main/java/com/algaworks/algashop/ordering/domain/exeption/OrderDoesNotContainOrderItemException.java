package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.*;

public class OrderDoesNotContainOrderItemException extends DomainExeption {

    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }
}
