/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
