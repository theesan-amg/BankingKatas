import org.example.Account;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
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
    void shouldPrintStatementWithOneWithdraw() {
        Account account = new Account(fixedClock(2026, 5, 13));

        account.withdraw(200);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", -200, -200);

        assertEquals(expected, account.printStatement());
    }

    @Test
    void shouldPrintStatementInReverseChronologicalOrderWithRunningBalance() {
        MutableClock clock = new MutableClock(LocalDate.of(2026, 5, 13), ZoneId.systemDefault());
        Account account = new Account(clock);

        account.deposit(1000);

        clock.setDate(LocalDate.of(2026, 5, 14));
        account.withdraw(300);

        String expected =
                String.format("%-12s %8s %8s%n", "Date", "Amount", "Balance") +
                        String.format("%-12s %+8d %8d%n", "14.05.2026", -300, 700) +
                        String.format("%-12s %+8d %8d%n", "13.05.2026", 1000, 1000);

        assertEquals(expected, account.printStatement());
    }


    private Clock fixedClock(int year, int month, int day) {
        return Clock.fixed(
                LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault()
        );
    }

    static class MutableClock extends Clock {
        private Instant instant;
        private final ZoneId zone;

        MutableClock(LocalDate date, ZoneId zone) {
            this.zone = zone;
            this.instant = date.atStartOfDay(zone).toInstant();
        }

        void setDate(LocalDate date) {
            this.instant = date.atStartOfDay(zone).toInstant();
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new MutableClock(LocalDate.ofInstant(instant, zone), zone);
        }

        @Override
        public Instant instant() {
            return instant;
        }
    }
}