package com.algaworks.algashop.ordering.core.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductId;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM;
import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId itemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, itemId));
    }

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}
