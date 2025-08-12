package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS;

public class OrderCannotBePlacedExeption extends DomainExeption {

    public OrderCannotBePlacedExeption(OrderId id) {
        super(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS, id));
    }
}
