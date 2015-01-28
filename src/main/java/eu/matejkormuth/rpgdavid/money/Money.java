package eu.matejkormuth.rpgdavid.money;

import com.google.common.base.Preconditions;

public class Money {
    private Currency currency;
    private int amount;

    public static Money of(int amount, Currency currency) {
        return new Money(amount, currency);
    }

    public Money(int amount, Currency currency) {
        Preconditions.checkNotNull(currency);

        this.amount = amount;
        this.currency = currency;
    }

    private FormattingContext getFormattingContext() {
        return FormattingContext.DEFAULT_FORMATTING_CONTEXT;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return getFormattingContext().format(this);
    }

    public Money add(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new RuntimeException("Can't add two different currencies!");
        }

        this.amount += money.amount;
        return this;
    }

    public Money subtract(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new RuntimeException("Can't add two different currencies!");
        }

        this.amount -= money.amount;
        return this;
    }

    public Money multiply(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new RuntimeException("Can't add two different currencies!");
        }

        this.amount *= money.amount;
        return this;
    }

    public Money divide(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new RuntimeException("Can't add two different currencies!");
        }

        this.amount /= money.amount;
        return this;
    }
}
