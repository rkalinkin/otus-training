package ru.otus.atm;

public class Cell {
    private final int denomination;
    private int count;

    public Cell(int denomination) {
        this.denomination = denomination;
        this.count = 0;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }

    public void addBanknotes(int count) {
        if (count < 0) throw new IllegalArgumentException("Count cannot be negative");
        this.count += count;
    }

    public void withdrawBanknote() {
        if (count == 0) throw new IllegalStateException("No banknotes available");
        count--;
    }
}
