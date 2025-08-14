package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartId;

public class ShoppingCartItemTestDataBuilder {

    private ShoppingCartId shoppingCartId = new ShoppingCartId();
    private Quantity quantity = new Quantity(1);
    private Boolean avaliable = true;
    private Product product = ProductTestDataBuilder.aProduct().build();

    private ShoppingCartItemTestDataBuilder() {
    }

    public static ShoppingCartItemTestDataBuilder aShoppingCartItem() {
        return new ShoppingCartItemTestDataBuilder();
    }

    public ShoppingCartItem build() {
        return ShoppingCartItem.brandNew()
                .shoppingCartId(shoppingCartId)
                .product(product)
                .quantity(quantity)
                .build();
    }

    public ShoppingCartItemTestDataBuilder shoppingCartId(ShoppingCartId shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public ShoppingCartItemTestDataBuilder quantity(Quantity quantity) {
        this.quantity = quantity;
        return this;
    }

    public ShoppingCartItemTestDataBuilder setProduct(Product product) {
        this.product = product;
        return this;
    }

    public ShoppingCartItemTestDataBuilder setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
        return this;
    }
}
