package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exeption.ShoppingCartDoesNotContainItemException;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ShoppingCartItemId;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ShoppingCartTest {
    @Test
    void givenCustomer_whenStartShopping_shouldInitializeEmptyCart() {
        CustomerId customerId = new CustomerId();
        ShoppingCart cart = ShoppingCart.startShopping(customerId);
        assertWith(cart,
                c -> assertThat(c.id()).isNotNull(),
                c -> assertThat(c.customerId()).isEqualTo(customerId),
                c -> assertThat(c.totalAmount()).isEqualTo(Money.ZERO),
                c -> assertThat(c.totalItems()).isEqualTo(Quantity.ZERO),
                c -> assertThat(c.isEmpty()).isTrue(),
                c -> assertThat(c.items()).isEmpty()
        );
    }

    @Test
    void givenEmptyCart_whenAddNewItem_shouldContainItemAndRecalculateTotals() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(2));
        assertThat(cart.items()).hasSize(1);

        ShoppingCartItem item = cart.items().iterator().next();

        assertWith(item,
                i -> assertThat(item.productId()).isEqualTo(product.id()),
                i -> assertThat(item.quantity()).isEqualTo(new Quantity(2)),
                i -> assertThat(cart.totalItems()).isEqualTo(new Quantity(2)),
                i -> assertThat(cart.totalAmount()).isEqualTo(new Money(product.price().value().multiply(new BigDecimal(2))))
        );
    }

    @Test
    void givenCartWithExistingProduct_whenAddSameProduct_shouldIncrementQuantity() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(3));
        cart.addItem(product, new Quantity(3));

        ShoppingCartItem cartItem = cart.items().iterator().next();

        assertThat(cart.items()).hasSize(1);
        assertThat(cartItem.quantity()).isEqualTo(new Quantity(6));
    }

    @Test
    void givenCartWithItems_whenRemoveExistingItem_shouldRemoveAndRecalculateTotals() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();
        ShoppingCartItem item = cart.items().iterator().next();

        cart.removeItem(item.id());

        assertThat(cart.items()).doesNotContain(item);
        assertThat(cart.totalItems()).isEqualTo(new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum()));
    }

    @Test
    void givenCartWithItems_whenRemoveNonexistentItem_shouldThrowShoppingCartDoesNotContainItemException() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();
        ShoppingCartItemId randomId = new ShoppingCartItemId();

        assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class)
                .isThrownBy(() -> cart.removeItem(randomId));
    }

    @Test
    void givenCartWithItems_whenEmpty_shouldClearAllItemsAndResetTotals() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();

        cart.empty();

        assertWith(cart,
                c -> assertThat(c.isEmpty()).isTrue(),
                c -> assertThat(c.totalItems()).isEqualTo(Quantity.ZERO),
                c -> assertThat(c.totalAmount()).isEqualTo(Money.ZERO)
        );
    }

    @Test
    void givenCartWithItems_whenChangeItemPrice_shouldRecalculateTotalAmount() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(2));

        Product updateProduct = product
                .toBuilder()
                .price(new Money("100"))
                .build();

        cart.refreshItem(updateProduct);

        ShoppingCartItem item = cart.findItem(product.id());

        assertThat(item.price()).isEqualTo(new Money("100"));
        assertThat(cart.totalAmount()).isEqualTo(new Money("200"));
    }

    @Test
    void givenCartWithAvailableItem_whenProductBecomesUnavailable_thenCartShouldDetectIt() {
        CustomerId customerId = new CustomerId();
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        Product availableProduct = ProductTestDataBuilder
                .aProduct()
                .inStock(true)
                .build();

        ProductId productId = availableProduct.id();

        cart.addItem(availableProduct, new Quantity(1));

        assertThat(cart.isContainsUnavailableItems()).isFalse();

        Product unavailableProduct = ProductTestDataBuilder
                .aProduct()
                .inStock(false)
                .id(productId)
                .build();

        cart.refreshItem(unavailableProduct);

        assertThat(cart.isContainsUnavailableItems()).isTrue();
    }


    @Test
    void givenCartWithItems_whenChangeQuantityToZero_shouldThrowIllegalArgumentException() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();
        ShoppingCartItem item = cart.items().iterator().next();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> cart.changeItemQuantity(item.id(), Quantity.ZERO));
    }

    @Test
    void givenCartWithItems_whenChangeItemQuantity_shouldRecalculateTotalItems() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();
        ShoppingCartItem item = cart.items().iterator().next();

        cart.changeItemQuantity(item.id(), new Quantity(5));

        assertThat(cart.totalItems()).isEqualTo(new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum()));
    }

    @Test
    void givenCartWithItems_whenFindItemById_shouldReturnItem() {
        ShoppingCart cart = ShoppingCartTestBuilder.aShoppingCart().build();
        ShoppingCartItem item = cart.items().iterator().next();
        ShoppingCartItem found = cart.findItem(item.id());
        assertThat(found).isEqualTo(item);
    }

    @Test
    void givenDifferentIds_whenCompareItems_shouldNotBeEqual() {
        var shoppingCart1 = ShoppingCartTestBuilder.aShoppingCart().build();
        var shoppingCart2 = ShoppingCartTestBuilder.aShoppingCart().build();
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
    }

    @Test
    void givenTwoDifferentProducts_whenAddedToCart_shouldContainBothItems() {
        Product productA = ProductTestDataBuilder.aProductAltMousePad().build();
        Product productB = ProductTestDataBuilder.aProductAltRamMemory().build();

        ShoppingCart shoppingCart = ShoppingCartTestBuilder.aShoppingCart().withItems(false).build();
        shoppingCart.addItem(productA, new Quantity(2));
        shoppingCart.addItem(productB, new Quantity(5));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(shoppingCart.items()).hasSize(2);
            softly.assertThat(shoppingCart.totalItems()).isEqualTo(new Quantity(7));
            softly.assertThat(shoppingCart.totalAmount()).isEqualTo(new Money("1200"));

            softly.assertThat(shoppingCart.items())
                    .anySatisfy(item -> {
                        softly.assertThat(item.productId()).isEqualTo(productA.id());
                        softly.assertThat(item.quantity()).isEqualTo(new Quantity(2));
                    });

            softly.assertThat(shoppingCart.items())
                    .anySatisfy(item -> {
                        softly.assertThat(item.productId()).isEqualTo(productB.id());
                        softly.assertThat(item.quantity()).isEqualTo(new Quantity(5));
                    });
        });
    }
}