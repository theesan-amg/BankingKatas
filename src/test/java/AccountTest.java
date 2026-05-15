import org.example.Account;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    public void shouldPrintEmptyStatement() {
        // Ca crée une instance de Account avec une horloge fixe
        Account account = new Account(fixedClock(2026, 5, 13));

        // le résultat attendu
        String expected = String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance");

        // Appelez la méthode printStatement
        String actual = account.printStatement();

        // Ca permet de vérifiez que le résultat correspond à l'attendu
        assertEquals(expected, actual);
    }

    @Test
    void shouldPrintStatementWithOneDeposit() {
        Account account = new Account(fixedClock(2026, 5, 13));

        account.deposit(500);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", 500, 500);

        assertEquals(expected, account.printStatement());
    }


    private Clock fixedClock(int year, int month, int day) {
        return Clock.fixed(
                LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );
    }
}