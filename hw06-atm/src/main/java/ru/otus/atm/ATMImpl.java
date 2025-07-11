package ru.otus.atm;

import java.util.List;

public class ATMImpl implements ATM {
    private final CashDispenser cashDispenser;

    public ATMImpl(CashDispenser cashDispenser) {
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void loadCash(Denomination denomination, int count) throws InvalidDenominationException {
        cashDispenser.addBanknotes(denomination, count);
    }

    @Override
    public List<Integer> withdraw(int amount) throws NotEnoughMoneyException {
        return cashDispenser.dispense(amount);
    }

    @Override
    public int checkBalance() {
        return cashDispenser.getTotalBalance();
    }
}
