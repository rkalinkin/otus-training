package ru.otus.atm;

public class Cell {
    private final Denomination denomination;
    private int count;

    public Cell(Denomination denomination) {
        this.denomination = denomination;
        this.count = 0;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }

    public void addBanknotes(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        this.count += count;
    }

    public void withdrawBanknote() {
        if (count == 0) {
            throw new IllegalStateException("No banknotes available");
        }
        count--;
    }

    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        this.count = count;
    }
}
