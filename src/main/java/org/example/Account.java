package org.example;

import java.time.Clock;

public class Account {

    private static final String HEADER_FORMAT = "%-12s %8s %8s%n";
    private final Clock clock;

    public Account(Clock clock) {
        this.clock = clock;
    }

    public String printStatement() {
        return String.format(HEADER_FORMAT, "Date", "Amount", "Balance");
    }

    public void deposit(int amount) {
    }

    public void withdraw(int amount) {
    }
}