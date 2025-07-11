package ru.otus.atm;

import java.util.*;

public class CashDispenser {
    private final List<Cell> cells = new ArrayList<>();

    public void addCell(Cell cell) {
        Objects.requireNonNull(cell, "Cell cannot be null");

        for (Cell value : cells) {
            if (value.getDenomination() == cell.getDenomination()) {
                value.addBanknotes(cell.getCount());
                return;
            }
        }
        cells.add(cell);
        sortCellsByDenominationDescending();
    }

    private void sortCellsByDenominationDescending() {
        cells.sort((c1, c2) -> Integer.compare(
                c2.getDenomination().getValue(), c1.getDenomination().getValue()));
    }

    public void addBanknotes(Denomination denomination, int count) throws InvalidDenominationException {
        for (Cell cell : cells) {
            if (cell.getDenomination() == denomination) {
                cell.addBanknotes(count);
                return;
            }
        }
        throw new InvalidDenominationException("Denomination not supported: " + denomination);
    }

    public int getTotalBalance() {
        return cells.stream()
                .mapToInt(cell -> cell.getDenomination().getValue() * cell.getCount())
                .sum();
    }

    public List<Integer> dispense(int amount) throws NotEnoughMoneyException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Map<Denomination, Integer> originalState = new HashMap<>();
        for (Cell cell : cells) {
            originalState.put(cell.getDenomination(), cell.getCount());
        }

        List<Integer> result = new ArrayList<>();
        int remaining = amount;

        for (Cell cell : cells) {
            int denomination = cell.getDenomination().getValue();
            int quantity = remaining / denomination;
            int toTake = Math.min(quantity, cell.getCount());

            for (int i = 0; i < toTake; i++) {
                cell.withdrawBanknote();
                result.add(denomination);
            }

            remaining -= denomination * toTake;
        }

        if (remaining != 0) {
            for (Map.Entry<Denomination, Integer> entry : originalState.entrySet()) {
                for (Cell cell : cells) {
                    if (cell.getDenomination() == entry.getKey()) {
                        cell.setCount(entry.getValue());
                    }
                }
            }
            throw new NotEnoughMoneyException("Cannot dispense exact amount: " + amount);
        }

        return result;
    }
}
