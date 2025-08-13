package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.OrderInvalidShippingDeliveryDateExeption;
import com.algaworks.algashop.ordering.domain.exeption.OrderStatusCannotBeChangeExeption;
import com.algaworks.algashop.ordering.domain.exeption.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        Address address = Address.builder()
                .state("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .city("York")
                .street("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build();

        BillingInfo billingInfo = BillingInfo.builder()
                .address(address)
                .document(new Document("123-45-6789"))
                .phone(new Phone("23-456-7890"))
                .fullName(new FullName("Cristian", "Puhl"))
                .build();

        Order order = Order.draft(new CustomerId());

        order.changeBilling(billingInfo);

        assertThat(order.billing()).isEqualTo(billingInfo);
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
}