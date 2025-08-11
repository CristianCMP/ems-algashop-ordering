package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.OrderStatusCannotBeChangeExeption;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order draft = Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        assertWith(orderItem,
                (i) -> assertThat(i.id()).isNotNull(),
                (i) -> assertThat(i.productId()).isEqualTo(productId),
                (i) -> assertThat(i.productName()).isEqualTo(new ProductName("Mouse pad")),
                (i) -> assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Mouse pad"),
                new Money("100"),
                new Quantity(2)
        );

        order.addItem(
                productId,
                new ProductName("RAM Memory"),
                new Money("50"),
                new Quantity(1)
        );

        assertThat(order.totalAmount()).isEqualTo(new Money("250"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givinDraftOrder_whenPlace_shouldChangetoPlaced() {
        Order order = Order.draft(new CustomerId());
        order.place();
        assertThat(order.isPlaced()).isTrue();
    }
    @Test
    public void givinPlacedOrder_whenTryToPlace_shouldGenerateException() {
        Order order = Order.draft(new CustomerId());
        order.place();
        assertThatExceptionOfType(OrderStatusCannotBeChangeExeption.class)
                .isThrownBy(order::place);
    }
}