package ru.otus;

import java.util.List;
import ru.otus.atm.*;

public class Main {
    public static void main(String[] args) {
        CashDispenser dispenser = new CashDispenser();
        dispenser.addCell(new Cell(Denomination.RUB_5000));
        dispenser.addCell(new Cell(Denomination.RUB_2000));
        dispenser.addCell(new Cell(Denomination.RUB_1000));

        ATM atm = new ATMImpl(dispenser);

        try {
            dispenser.addBanknotes(Denomination.RUB_5000, 10);
            dispenser.addBanknotes(Denomination.RUB_2000, 5);
            dispenser.addBanknotes(Denomination.RUB_1000, 20);

            System.out.println("Total balance: " + atm.checkBalance());

            int requested = 13000;
            List<Integer> bills = atm.withdraw(requested);
            System.out.printf("Dispensed %d using notes: %s%n", requested, bills);

        } catch (NotEnoughMoneyException | InvalidDenominationException e) {
            System.err.println(e.getMessage());
        }
    }
}
