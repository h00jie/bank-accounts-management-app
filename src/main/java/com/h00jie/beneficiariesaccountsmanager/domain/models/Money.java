package com.h00jie.beneficiariesaccountsmanager.domain.models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Money implements Comparable<Money> {

    @Schema(description = "Το ποσό σε δεκαδικό αριθμό", example = "250.00")
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money add(Money newAmount) {
        return new Money(this.amount.add(newAmount.amount));
    }

    public Money subtract(Money newAmount) {
        return new Money(this.amount.subtract(newAmount.amount));
    }

    public int compareTo(Money newAmount) {
        return this.amount.compareTo(newAmount.amount);
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }
}
