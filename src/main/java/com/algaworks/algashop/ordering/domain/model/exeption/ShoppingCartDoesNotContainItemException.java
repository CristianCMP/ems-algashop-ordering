package com.algaworks.algashop.ordering.domain.model.exeption;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT;
import static com.algaworks.algashop.ordering.domain.model.exeption.ErrorMenssages.ERROR_SHOPPING_CAR_DOES_NOT_CONTAIN_ITEM;

public class ShoppingCartDoesNotContainItemException extends DomainExeption {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId itemId) {
        super(String.format(ERROR_SHOPPING_CAR_DOES_NOT_CONTAIN_ITEM, id, itemId));
    }

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}
