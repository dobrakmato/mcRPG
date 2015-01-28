package eu.matejkormuth.rpgdavid.money;

public class FormattingContext {
    public static FormattingContext DEFAULT_FORMATTING_CONTEXT = new FormattingContext();

    public String format(Money money) {
        return Integer.toString(money.getAmount())
                + money.getCurrency().getAbbr();
    }
}
