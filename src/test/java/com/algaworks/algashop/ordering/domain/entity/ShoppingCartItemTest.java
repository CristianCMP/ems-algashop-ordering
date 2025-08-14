package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ShoppingCartItemTest {
    @Test
    void givenValidData_whenCreateNewItem_shouldInitializeCorrectly() {
        ShoppingCartItem item = ShoppingCartItemTestDataBuilder.aShoppingCartItem().build();

        assertWith(item,
                i -> assertThat(i.id()).isNotNull(),
                i -> assertThat(i.shoppingCartId()).isNotNull(),
                i -> assertThat(i.productId()).isNotNull(),
                i -> assertThat(i.productName()).isEqualTo(new ProductName("Notebook X11")),
                i -> assertThat(i.price()).isEqualTo(new Money("3000")),
                i -> assertThat(i.quantity()).isEqualTo(new Quantity(1)),
                i -> assertThat(i.isAvailable()).isTrue(),
                i -> assertThat(i.totalAmount()).isEqualTo(new Money("3000"))
        );
    }

    @Test
    void givenItem_whenChangeQuantity_shouldRecalculateTotal() {
        ShoppingCartItem item = ShoppingCartItemTestDataBuilder.aShoppingCartItem()
                .quantity(new Quantity(2))
                .build();

        item.changeQuantity(new Quantity(3));

        assertWith(item,
                i -> assertThat(i.quantity()).isEqualTo(new Quantity(3)),
                i -> assertThat(i.totalAmount()).isEqualTo(new Money("9000"))
        );
    }

    @Test
    void givenItemWithQuantity_whenPriceChanges_thenTotalShouldBeRecalculated() {
        ProductId productId = new ProductId();
        Money initialPrice = new Money("10.00");
        Quantity quantity = new Quantity(2);

        Product initialProduct = ProductTestDataBuilder.aProduct()
                .id(productId)
                .price(initialPrice)
                .build();

        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .shoppingCartId(new ShoppingCartId())
                .product(initialProduct)
                .quantity(quantity)
                .build();

        assertThat(item.price()).isEqualTo(initialPrice);
        assertThat(item.totalAmount()).isEqualTo(initialPrice.multiply(quantity));

        Money newPrice = new Money("15.00");
        Product updatedProduct = ProductTestDataBuilder.aProduct()
                .id(productId)
                .price(newPrice)
                .build();

        item.refresh(updatedProduct);

        assertThat(item.price()).isEqualTo(newPrice);
        assertThat(item.totalAmount()).isEqualTo(newPrice.multiply(quantity));
    }


    @Test
    void givenAvailableItem_whenProductBecomesUnavailable_thenStatusShouldUpdate() {
        ProductId productId = new ProductId();
        Product initialProduct = ProductTestDataBuilder.aProduct()
                .id(productId)
                .inStock(true)
                .name(new ProductName("Product original"))
                .price(new Money("10.00"))
                .build();

        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .shoppingCartId(new ShoppingCartId())
                .product(initialProduct)
                .quantity(new Quantity(1))
                .build();

        assertThat(item.isAvailable()).isTrue();

        Product updatedProduct = ProductTestDataBuilder.aProduct()
                .id(productId)
                .inStock(false)
                .name(new ProductName("Product update"))
                .price(new Money("12.50"))
                .build();

        item.refresh(updatedProduct);

        assertWith(item,
                i -> assertThat(i.isAvailable()).isFalse(),
                i -> assertThat(i.productName()).isEqualTo(updatedProduct.name()),
                i -> assertThat(i.price()).isEqualTo(updatedProduct.price())
        );
    }

    @Test
    void givenEqualIds_whenCompareItems_shouldBeEqual() {
        ShoppingCartId cartId = new ShoppingCartId();
        ProductId productId = new ProductId();
        ShoppingCartItemId shoppingCartItemId = new ShoppingCartItemId();

        ShoppingCartItem item1 = ShoppingCartItem.existing()
                .id(shoppingCartItemId)
                .shoppingCartId(cartId)
                .productId(productId)
                .productName(new ProductName("Mouse"))
                .price(new Money("100"))
                .quantity(new Quantity(1))
                .available(true)
                .totalAmount(new Money("100"))
                .build();

        ShoppingCartItem item2 = ShoppingCartItem.existing()
                .id(shoppingCartItemId)
                .shoppingCartId(cartId)
                .productId(productId)
                .productName(new ProductName("Notebook"))
                .price(new Money("100"))
                .quantity(new Quantity(1))
                .available(true)
                .totalAmount(new Money("100"))
                .build();

        assertThat(item1).isEqualTo(item2);
        assertThat(item1.hashCode()).hasSameHashCodeAs(item2.hashCode());
    }

    @Test
    void givenDifferentIds_whenCompareItems_shouldNotBeEqual() {
        ShoppingCartItem item1 = ShoppingCartItemTestDataBuilder.aShoppingCartItem().build();
        ShoppingCartItem item2 = ShoppingCartItemTestDataBuilder.aShoppingCartItem().build();
        assertThat(item1).isNotEqualTo(item2);
    }
}