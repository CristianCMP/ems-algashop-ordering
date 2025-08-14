package com.algaworks.algashop.ordering.domain.exeption;

import com.algaworks.algashop.ordering.domain.valueobject.id.*;

import static com.algaworks.algashop.ordering.domain.exeption.ErrorMenssages.*;

public class ShoppingCartDoesNotContainItemException extends DomainExeption {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId itemId) {
        super(String.format(ERROR_SHOPPING_CAR_DOES_NOT_CONTAIN_ITEM, id, itemId));
    }

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}
