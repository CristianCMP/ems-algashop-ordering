package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder.existingCustomer;
import static com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder.aBilling;
import static com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder.aShipping;
import static com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod.CREDIT_CARD;
import static com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod.GATEWAY_BALANCE;
import static com.algaworks.algashop.ordering.domain.model.entity.ProductTestDataBuilder.aProduct;
import static com.algaworks.algashop.ordering.domain.model.entity.ProductTestDataBuilder.aProductUnavailable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BuyNowServiceTest {

    private final BuyNowService buyNowService = new BuyNowService();

    @Test
    void givenValidBuyNowProduct_when_shouldAddOrderItemAndPlaceOrder() {
        final var product = aProduct().build();
        final var customer = existingCustomer().build();
        final var billing = aBilling();
        final var shipping = aShipping();
        final var quantity = new Quantity(2);
        final var totalAmount = product.price().multiply(quantity).add(shipping.cost());

        final var orderBuyNow = buyNowService.buyNow(product, customer.id(), billing, shipping, quantity, CREDIT_CARD);

        assertThat(orderBuyNow).isNotNull();
        assertThat(orderBuyNow.customerId()).isEqualTo(customer.id());
        assertThat(orderBuyNow.paymentMethod()).isEqualTo(CREDIT_CARD);
        assertThat(orderBuyNow.billing()).isEqualTo(billing);
        assertThat(orderBuyNow.shipping()).isEqualTo(shipping);
        assertThat(orderBuyNow.totalItems()).isEqualTo(quantity);
        assertThat(orderBuyNow.totalAmount()).isEqualTo(totalAmount);
        assertThat(orderBuyNow.items()).hasSize(1)
                .extracting("productId")
                .containsExactlyInAnyOrder(product.id());
    }

    @Test
    void givenProductUnavailable_whenBuyNow_shouldThrowProductOutOfStockException() {
        final var product = aProductUnavailable().build();
        final var customer = existingCustomer().build();
        final var billing = aBilling();
        final var shipping = aShipping();
        final var quantity = new Quantity(2);

        assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(() -> buyNowService.buyNow(product, customer.id(), billing, shipping, quantity, GATEWAY_BALANCE));
    }

    @Test
    void givenInvalidProduct_when_BuyNow_shouldThrowIllegalArgumentException() {
        final var product = aProduct().build();
        final var customer = existingCustomer().build();
        final var billing = aBilling();
        final var shipping = aShipping();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buyNowService.buyNow(product, customer.id(), billing, shipping, Quantity.ZERO, GATEWAY_BALANCE));
    }

}