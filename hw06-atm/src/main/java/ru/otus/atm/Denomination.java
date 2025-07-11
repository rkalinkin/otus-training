package ru.otus.atm;

public enum Denomination {
    RUB_100(100),
    RUB_500(500),
    RUB_1000(1000),
    RUB_2000(2000),
    RUB_5000(5000);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Denomination fromValue(int value) {
        for (Denomination d : values()) {
            if (d.getValue() == value) {
                return d;
            }
        }
        throw new InvalidDenominationException("Denomination not supported: " + value);
    }
}
