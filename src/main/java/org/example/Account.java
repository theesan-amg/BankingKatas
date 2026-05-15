package org.example;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private static final String HEADER_FORMAT = "%-12s %8s %8s%n";
    private static final String LINE_FORMAT = "%-12s %+8d %8d%n";

    private final List<Transaction> transactions = new ArrayList<>();
    private final Clock clock;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Account(Clock clock) {
        this.clock = clock;
    }

    public void deposit(int amount) {
        transactions.add(new Transaction(LocalDate.now(clock), amount));
    }

    public void withdraw(int amount) {
        transactions.add(new Transaction(LocalDate.now(clock), -amount));
    }

    public String printStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(HEADER_FORMAT, "Date", "Amount", "Balance"));

        int balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.amount();
            sb.append(String.format(
                    LINE_FORMAT,
                    transaction.date().format(formatter),
                    transaction.amount(),
                    balance
            ));
        }

        return sb.toString();
    }
}