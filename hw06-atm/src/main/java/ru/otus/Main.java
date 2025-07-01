package ru.otus;

import java.util.List;
import ru.otus.atm.*;

public class Main {
    public static void main(String[] args) {
        CashDispenser dispenser = new CashDispenser();
        dispenser.addCell(new Cell(5000));
        dispenser.addCell(new Cell(2000));
        dispenser.addCell(new Cell(1000));

        ATM atm = new ATM(dispenser);

        try {
            System.out.println("Loading cash...");
            dispenser.getCell(5000).addBanknotes(10);
            dispenser.getCell(2000).addBanknotes(5);
            dispenser.getCell(1000).addBanknotes(20);

            System.out.println("Total balance: " + atm.checkBalance());

            int requested = 13000;
            List<Integer> bills = atm.withdraw(requested);
            System.out.printf("Dispensed %d using notes: %s%n", requested, bills);

        } catch (NotEnoughMoneyException | InvalidDenominationException e) {
            System.err.println(e.getMessage());
        }
    }
}
