package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.*;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    @Test
    public void shouldGenerateDraftOrder() {
        CustomerId customerId = new CustomerId();
        Order order = Order.draft(customerId);

        assertWith(order,
                o -> assertThat(o.id()).isNotNull(),
                o -> assertThat(o.customerId()).isEqualTo(customerId),
                o -> assertThat(o.totalAmount()).isEqualTo(Money.ZERO),
                o -> assertThat(o.totalItems()).isEqualTo(Quantity.ZERO),
                o -> assertThat(o.isDraft()).isTrue(),
                o -> assertThat(o.items()).isEmpty(),

                o -> assertThat(o.placedAt()).isNull(),
                o -> assertThat(o.paidAt()).isNull(),
                o -> assertThat(o.canceledAt()).isNull(),
                o -> assertThat(o.readyAt()).isNull(),
                o -> assertThat(o.billing()).isNull(),
                o -> assertThat(o.shipping()).isNull(),
                o -> assertThat(o.paymentMethod()).isNull()
        );
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();
        ProductId productId = product.id();

        order.addItem(product, new Quantity(1));

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        assertWith(orderItem,
                (i) -> assertThat(i.id()).isNotNull(),
                (i) -> assertThat(i.productId()).isEqualTo(productId),
                (i) -> assertThat(i.productName()).isEqualTo(new ProductName("Mouse Pad")),
                (i) -> assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));

        Set<OrderItem> items = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(ProductTestDataBuilder.aProductAltMousePad().build(), new Quantity(2));
        order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));

        assertThat(order.totalAmount()).isEqualTo(new Money("400"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangetoPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenMackAsPaid_shouldChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();

        assertThat(order.isPaid()).isTrue();
        assertThat(order.paidAt()).isNotNull();
    }

    @Test
    public void givenPlacedOrder_whenTryToPlace_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangeExeption.class)
                .isThrownBy(order::place);
    }

    @Test
    public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());
        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);
        assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void givenDraftOrder_whenChangeBilling_shouldAllowChange() {
        Billing billing = OrderTestDataBuilder.aBilling();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billing);

        assertThat(order.billing()).isEqualTo(billing);
    }

    @Test
    public void givenDraftOrder_whenChangeShipping_shouldAllowChange() {
        Shipping shipping = OrderTestDataBuilder.aShipping();

        Order order = Order.draft(new CustomerId());

        order.changeShipping(shipping);

        assertThat(order.shipping()).isEqualTo(shipping);
    }

    @Test
    public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShipping_shouldNotAllowChange() {
        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(1);

        Shipping shipping = OrderTestDataBuilder.aShipping().toBuilder()
                .expectedDate(expectedDeliveryDate)
                .build();

        Order order = Order.draft(new CustomerId());

        assertThatExceptionOfType(OrderInvalidShippingDeliveryDateExeption.class)
                .isThrownBy(() -> order.changeShipping(shipping));
    }

    @Test
    public void givenDraftOrder_whenChangeItem_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(3));

        OrderItem orderItem = order.items().iterator().next();

        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        assertWith(order,
                (o) -> assertThat(o.totalAmount()).isEqualTo(new Money("500")),
                (o) -> assertThat(o.totalItems()).isEqualTo(new Quantity(5))
        );
    }

    @Test
    public void givenOutOfStockProduct_whenTryToAddToAnOrder_shouldNotAllow() {
        Order order = Order.draft(new CustomerId());

        ThrowableAssert.ThrowingCallable addItemTask = () -> order.addItem(ProductTestDataBuilder.aProductUnavailable().build(), new Quantity(1));

        assertThatExceptionOfType(ProductOutOfStockException.class).isThrownBy(addItemTask);
    }

    @Test
    public void givenPlacedOrder_whenTryToAddToAnOrder_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.place();

        assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.addItem(product, new Quantity(3)));
    }

    @Test
    public void giveOrder_whenRemoveItem_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        order.removeItem(orderItem.id());

        assertWith(order,
                (o) -> assertThat(o.items()).isEmpty(),
                (o) -> assertThat(o.totalItems()).isEqualTo(Quantity.ZERO),
                (o) -> assertThat(o.totalAmount()).isEqualTo(Money.ZERO)
        );
    }

    @Test
    public void giveOrder_whenRemoveItemNonexistent_shouldNotAllowChange() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));

        assertThat(order.items().size()).isEqualTo(1);
        assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(() -> order.removeItem(new OrderItemId()));
    }

    @Test
    public void givenPlaceOrder_whenRemoveItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));
        order.place();

        OrderItem orderItem = order.items().iterator().next();

        assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.removeItem(orderItem.id()));
    }

    @Test
    public void givePlacedOrder_whenMarkAsPaid_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.place();
        order.markAsPaid();

        assertWith(order,
                (o) -> assertThat(o.isPaid()).isTrue(),
                (o) -> assertThat(o.paidAt()).isNotNull()
        );
    }

    @Test
    public void givePlacedOrder_whenMarkAsPaid_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        assertThatExceptionOfType(OrderStatusCannotBeChangeExeption.class)
                .isThrownBy(order::markAsPaid);
    }

    @Test
    public void givePaidOrder_whenMarkAsPaid_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.place();
        order.markAsPaid();
        order.markAsReady();

        assertWith(order,
                (o) -> assertThat(o.isReady()).isTrue(),
                (o) -> assertThat(o.readyAt()).isNotNull()
        );
    }

    @Test
    public void givePaidOrder_whenMarkAsPaid_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        assertThatExceptionOfType(OrderStatusCannotBeChangeExeption.class)
                .isThrownBy(order::markAsReady);
    }

    @Test
    public void givenOrderInDraftPlacedPaidOrReady_whenCancel_shouldChangeToCanceled() {
        OrderStatus[] allowedStatuses = {
                OrderStatus.DRAFT,
                OrderStatus.PLACED,
                OrderStatus.PAID,
                OrderStatus.READY
        };

        for (OrderStatus status : allowedStatuses) {
            Order order = OrderTestDataBuilder.anOrder().status(status).build();
            order.cancel();

            assertWith(order,
                    o -> assertThat(o.isCanceled()).isTrue(),
                    o -> assertThat(o.status()).isEqualTo(OrderStatus.CANCELED),
                    o -> assertThat(o.canceledAt()).isNotNull()
            );
        }
    }

    @Test
    public void givenCanceledOrder_whenCancelAgain_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.cancel();

        OffsetDateTime canceledAtBefore = order.canceledAt();

        assertWith(order,
                o -> assertThatExceptionOfType(OrderStatusCannotBeChangeExeption.class).isThrownBy(order::cancel),
                o -> assertThat(o.status()).isEqualTo(OrderStatus.CANCELED),
                o -> assertThat(o.canceledAt()).isEqualTo(canceledAtBefore)
        );
    }

    @Test
    public void isCanceled_shouldReturnTrueOnlyWhenStatusIsCanceled() {
        Order order = OrderTestDataBuilder.anOrder().build();
        assertThat(order.isCanceled()).isFalse();

        order.place();
        assertThat(order.isCanceled()).isFalse();

        order.markAsPaid();
        assertThat(order.isCanceled()).isFalse();

        order.markAsReady();
        assertThat(order.isCanceled()).isFalse();

        order.cancel();
        assertThat(order.isCanceled()).isTrue();
    }
}