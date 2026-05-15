package org.example;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {

    private static final String HEADER_FORMAT = "%-12s %8s %8s%n";
    private static final String LINE_FORMAT = "%-12s %+8d %8d%n";

    private final Clock clock;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private Integer amount;

    public Account(Clock clock) {
        this.clock = clock;
    }

    public void deposit(int amount) {
        this.amount = amount;
    }

    public void withdraw(int amount) {
    }

    public String printStatement() {
        String header = String.format(HEADER_FORMAT, "Date", "Amount", "Balance");

        if (amount == null) {
            return header;
        }

        return header + String.format(
                LINE_FORMAT,
                LocalDate.now(clock).format(formatter),
                amount,
                amount
        );
    }
}