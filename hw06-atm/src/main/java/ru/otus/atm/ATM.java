package ru.otus.atm;

import java.util.List;

public class ATM {
    private final CashDispenser cashDispenser;

    public ATM(CashDispenser cashDispenser) {
        this.cashDispenser = cashDispenser;
    }

    public void loadCash(int denomination, int count) throws InvalidDenominationException {
        if (!cashDispenser.getAvailableDenominations().contains(denomination)) {
            throw new InvalidDenominationException("Denomination not supported: " + denomination);
        }
        cashDispenser.getCell(denomination).addBanknotes(count);
    }

    public List<Integer> withdraw(int amount) throws NotEnoughMoneyException {
        return cashDispenser.dispense(amount);
    }

    public int checkBalance() {
        return cashDispenser.getTotalBalance();
    }
}
