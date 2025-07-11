package ru.otus.atm;

import java.util.List;

public interface ATM {
    int checkBalance();

    List<Integer> withdraw(int amount) throws NotEnoughMoneyException;

    void loadCash(Denomination denomination, int count) throws InvalidDenominationException;
}
