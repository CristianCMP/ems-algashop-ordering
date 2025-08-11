package com.algaworks.algashop.ordering.domain.entity;

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
    public void shouldGenerateExeptionWhenTryToChangeItemSet() {
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
}