package ru.otus.atm;

import java.util.*;

public class CashDispenser {
    private final Map<Integer, Cell> cells = new TreeMap<>(Collections.reverseOrder());

    public void addCell(Cell cell) {
        int denomination = cell.getDenomination();
        if (cells.containsKey(denomination)) {
            cells.get(denomination).addBanknotes(cell.getCount());
        } else {
            cells.put(denomination, cell);
        }
    }

    public Cell getCell(int denomination) {
        Cell cell = cells.get(denomination);
        if (cell == null) {
            throw new IllegalArgumentException("Denomination not supported: " + denomination);
        }
        return cell;
    }

    public List<Integer> getAvailableDenominations() {
        return new ArrayList<>(cells.keySet());
    }

    public int getTotalBalance() {
        return cells.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue().getCount())
                .sum();
    }

    public List<Integer> dispense(int amount) throws NotEnoughMoneyException {
        if (amount < 0) throw new IllegalArgumentException("Amount must be positive");

        Map<Integer, Integer> originalState = new HashMap<>();
        for (var entry : cells.entrySet()) {
            originalState.put(entry.getKey(), entry.getValue().getCount());
        }

        List<Integer> result = new ArrayList<>();
        int remaining = amount;

        for (Map.Entry<Integer, Cell> entry : cells.entrySet()) {
            int denomination = entry.getKey();
            Cell cell = entry.getValue();

            int quantity = remaining / denomination;
            int toTake = Math.min(quantity, cell.getCount());

            for (int i = 0; i < toTake; i++) {
                cell.withdrawBanknote();
                result.add(denomination);
            }

            remaining -= denomination * toTake;
        }

        if (remaining != 0) {
            for (Map.Entry<Integer, Integer> entry : originalState.entrySet()) {
                Cell cell = cells.get(entry.getKey());
                cell.addBanknotes(entry.getValue() - cell.getCount());
            }
            throw new NotEnoughMoneyException("Cannot dispense exact amount: " + amount);
        }

        return result;
    }
}
