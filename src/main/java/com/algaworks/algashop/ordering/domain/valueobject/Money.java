package com.algaworks.algashop.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal value) implements Comparable<Money> {

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value);

        this.value = value.setScale(2, ROUNDING_MODE);

        if (this.value().signum() == -1) {
            throw new IllegalArgumentException();
        }
    }

//    public Money multuply(Quantity quantity) {
//        Objects.requireNonNull(quantity);
//
//        if (quantity.value() < 1) {
//            throw new IllegalArgumentException();
//        }
//
//        BigDecimal multiply = this.value.multiply(new BigDecimal(quantity.value()));
//        return new Money(multiply));
//    }

    public Money add(Money other) {
        Objects.requireNonNull(other);

        BigDecimal add = this.value.add(other.value());
        return new Money(add);
    }

    public Money divide(Money other) {
        Objects.requireNonNull(other);

        BigDecimal divide = this.value.divide(other.value(), ROUNDING_MODE);
        return new Money(divide);
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Money o) {
        return this.value().compareTo(o.value());
    }
}