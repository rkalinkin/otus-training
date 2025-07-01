package ru.otus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.*;

@DisplayName("Класс AtmTest")
class AtmTest {

    private ATM atm;

    @BeforeEach
    void setUp() {
        CashDispenser cashDispenser = new CashDispenser();
        cashDispenser.addCell(new Cell(5000));
        cashDispenser.addCell(new Cell(2000));
        cashDispenser.addCell(new Cell(1000));

        cashDispenser.getCell(5000).addBanknotes(10);
        cashDispenser.getCell(2000).addBanknotes(10);
        cashDispenser.getCell(1000).addBanknotes(10);

        atm = new ATM(cashDispenser);
    }

    @Test
    @DisplayName("проверка баланса")
    void testCheckBalance() {
        int balance = atm.checkBalance();
        assertEquals(5000 * 10 + 2000 * 10 + 1000 * 10, balance);
    }

    @Test
    @DisplayName("выдача одной купюрой")
    void testWithdrawExactAmountWithLargestNotes() throws Exception {
        List<Integer> result = atm.withdraw(5000);

        assertEquals(List.of(5000), result);
        assertEquals(5000 * 9 + 2000 * 10 + 1000 * 10, atm.checkBalance());
    }

    @Test
    @DisplayName("выдача двумя купюрами")
    void testWithdrawMultipleNotes() throws Exception {
        List<Integer> result = atm.withdraw(7000); // 5000 + 2000

        assertEquals(List.of(5000, 2000), result);
        assertEquals(5000 * 9 + 2000 * 9 + 1000 * 10, atm.checkBalance());
    }

    @Test
    @DisplayName("выдача мелкими купюрами")
    void testWithdrawOnlySmallerNotes() throws Exception {
        for (int i = 0; i < 10; i++) {
            atm.withdraw(2000); // тратим все 2000
        }
        assertEquals(5000 * 10 + 1000 * 10, atm.checkBalance());

        List<Integer> result = atm.withdraw(4000); // 1000 * 4
        assertEquals(List.of(1000, 1000, 1000, 1000), result);
        assertEquals(5000 * 10 + 1000 * 6, atm.checkBalance());
    }

    @Test
    @DisplayName("выбросить NotEnoughMoneyException когда недостаточно денег")
    void testNotEnoughMoney() {
        assertThrows(NotEnoughMoneyException.class, () -> atm.withdraw(100_000));
    }

    @Test
    @DisplayName("выбросить InvalidDenominationException когда используем несуществующий номинал")
    void testInvalidDenomination() {
        Exception exception = assertThrows(InvalidDenominationException.class, () -> atm.loadCash(123, 10));

        assertTrue(exception.getMessage().contains("Denomination not supported"));
    }
}
