package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exeption.OrderInvalidShippingDeliveryDateExeption;
import com.algaworks.algashop.ordering.domain.exeption.OrderStatusCannotBeChangeExeption;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
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
        Order order = OrderTestDataBuilder.anOrder().build();

        assertThat(order.totalAmount()).isEqualTo(new Money("6200"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangetoPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenMackAsPaid_shouldChangeToPaid(){
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
        Address address = Address.builder()
                .state("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .city("York")
                .street("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .document(new Document("123-45-6789"))
                .phone(new Phone("123-456-7890"))
                .fullName(new FullName("Cristian", "Puhl"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(1);

        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);

        assertWith(order,
                o -> assertThat(order.shipping()).isEqualTo(shippingInfo),
                o -> assertThat(order.shippingCost()).isEqualTo(shippingCost),
                o -> assertThat(order.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate)
        );
    }

    @Test
    public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShipping_shouldNotAllowChange() {
        Address address = Address.builder()
                .state("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .city("York")
                .street("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .document(new Document("123-45-6789"))
                .phone(new Phone("123-456-7890"))
                .fullName(new FullName("Cristian", "Puhl"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(1);

        assertThatExceptionOfType(OrderInvalidShippingDeliveryDateExeption.class)
                .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate));
    }
}