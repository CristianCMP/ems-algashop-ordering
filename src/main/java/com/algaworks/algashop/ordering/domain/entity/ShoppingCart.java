package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.ShoppingCartDoesNotContainItemException;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(of = "id")
public class ShoppingCart {

    private ShoppingCartId id;
    private CustomerId customerId;
    private Money totalAmount;
    private Quantity totalItems;
    private OffsetDateTime createdAt;
    private Set<ShoppingCartItem> items;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", buildMethodName = "existing")
    public ShoppingCart(
            ShoppingCartId id, CustomerId customerId, Money totalAmount,
            Quantity totalItems, OffsetDateTime createdAt, Set<ShoppingCartItem> items
    ) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setItems(items);
    }

    public static ShoppingCart startShopping(CustomerId customerId) {
        return new ShoppingCart(
                new ShoppingCartId(),
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                OffsetDateTime.now(),
                new HashSet<>()
        );
    }

    public ShoppingCartId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return Collections.unmodifiableSet(items);
    }

    public void empty() {
        this.items.clear();
        this.setTotalAmount(Money.ZERO);
        this.setTotalItems(Quantity.ZERO);
    }

    public void removeItem(ShoppingCartItemId itemId) {
        Objects.requireNonNull(itemId);

        ShoppingCartItem shoppingCartItem = findItem(itemId);

        this.items.remove(shoppingCartItem);
        this.recalculateTotals();
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.brandNew()
                .shoppingCartId(this.id())
                .product(product)
                .quantity(quantity)
                .build();

        if (this.items == null) {
            this.items = new HashSet<>();
        }

        if (this.existItem(product.id())){
            ShoppingCartItem item = findItem(product.id());
            this.updateItem(item, product, quantity);
        }else{
            this.items.add(shoppingCartItem);
        }

        this.recalculateTotals();
    }


    private void updateItem(ShoppingCartItem shoppingCartItem, Product product, Quantity quantity) {
        shoppingCartItem.refresh(product);
        shoppingCartItem.changeQuantity(shoppingCartItem.quantity().add(quantity));
    }

    public void refreshItem(Product product) {
        Objects.requireNonNull(product);

        ShoppingCartItem shoppingCartItem = findItem(product.id());
        shoppingCartItem.refresh(product);

        this.recalculateTotals();
    }

    public ShoppingCartItem findItem(ShoppingCartItemId itemId) {
        Objects.requireNonNull(itemId);
        return this.items()
                .stream()
                .filter(i -> i.id().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id(), itemId));
    }

    public ShoppingCartItem findItem(ProductId productId) {
        Objects.requireNonNull(productId);
        return this.items()
                .stream()
                .filter(i -> i.productId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id(), productId));
    }

    public boolean existItem(ProductId productId) {
        Objects.requireNonNull(productId);
        return this.items()
                .stream()
                .anyMatch(i -> i.productId().equals(productId));
    }


    public void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items
                .stream()
                .map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items
                .stream()
                .map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        this.setTotalAmount(new Money(totalItemsAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    public void changeItemQuantity(ShoppingCartItemId itemId, Quantity quantity) {
        Objects.requireNonNull(itemId);
        Objects.requireNonNull(quantity);

        ShoppingCartItem shoppingCartItem = findItem(itemId);
        shoppingCartItem.changeQuantity(quantity);

        this.recalculateTotals();
    }

    public boolean isContainsUnavailableItems() {
        return items()
                .stream()
                .anyMatch(i -> !i.isAvailable());
    }

    public boolean isEmpty() {
        return this.items().isEmpty();
    }

    public void setId(ShoppingCartId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    public void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    public void setTotalItems(Quantity totalItems) {
        Objects.requireNonNull(totalItems);
        this.totalItems = totalItems;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
    }

    public void setItems(Set<ShoppingCartItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }
}
