package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

class MoneyTest {

    private final static BigDecimal expected = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);


    @Test
    void shouldGenerateWithValueString() {
        Money money = new Money(String.valueOf(10));

        Assertions.assertThat(money.value()).isEqualTo(expected);
    }

    @Test
    void shouldGenerateWithValueBigDecimal() {
        Money money = new Money(BigDecimal.TEN);

        Assertions.assertThat(money.value()).isEqualTo(expected);
    }

    @Test
    void shouldAddValue() {
        Money money = new Money(BigDecimal.TEN);

        Money moneyAdded = money.add(new Money(BigDecimal.TEN));

        Assertions.assertThat(moneyAdded.value()).isEqualTo(new BigDecimal(20).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void shouldAddZeroValue() {
        Money money = new Money(BigDecimal.TEN);

        Money moneyAdded = money.add(Money.ZERO);

        Assertions.assertThat(moneyAdded.value()).isEqualTo(expected);
    }

    @Test
    void shouldNotAddValue() {
        Money money = new Money(BigDecimal.TEN);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> money.add(new Money(new BigDecimal(-10))));

        Assertions.assertThat(money.value()).isEqualTo(expected);
    }

    @Test
    void shouldDivideValue() {
        Money money = new Money(new BigDecimal(100));

        Money moneyDivided = money.divide(new Money(BigDecimal.TEN));

        Assertions.assertThat(moneyDivided.value()).isEqualTo(expected);
    }

    @Test
    void shouldNotDivideValue() {
        Money money = new Money(BigDecimal.TEN);

        Assertions.assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> money.divide(Money.ZERO));

        Assertions.assertThat(money.value()).isEqualTo(expected);
    }

    @Test
    void shouldMultiplyValue() {
        Money money = new Money(BigDecimal.TEN);

        Money moneyMultiplied = money.multiply(new Quantity(10));

        Assertions.assertThat(moneyMultiplied.value()).isEqualTo(new BigDecimal(100).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void shouldNotMultiplyValue() {
        Money money = new Money(BigDecimal.TEN);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> money.multiply(Quantity.ZERO));

        Assertions.assertThat(money.value()).isEqualTo(expected);
    }
}