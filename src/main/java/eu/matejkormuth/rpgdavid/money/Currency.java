package eu.matejkormuth.rpgdavid.money;

public class Currency {
    private final String name;
    private final String abbr;

    public Currency(String name, String abbr) {
        this.name = name;
        this.abbr = abbr;
    }

    public String getName() {
        return this.name;
    }

    public String getAbbr() {
        return this.abbr;
    }

    public Money toMoney(int amount) {
        return new Money(amount, this);
    }
}
