package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

public class ShoppingCartTestBuilder {
    public CustomerId customerId = new CustomerId();
    private boolean withItems = true;

    private ShoppingCartTestBuilder() {
    }

    public static ShoppingCartTestBuilder aShoppingCart() {
        return new ShoppingCartTestBuilder();
    }

    public ShoppingCart build() {
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        if (withItems) {
            cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));
            cart.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        }

        return cart;
    }

    public ShoppingCartTestBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public ShoppingCartTestBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }
}
