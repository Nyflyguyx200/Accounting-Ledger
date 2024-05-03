package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transactions {
    private final LocalDateTime dateTime;
    private final String description;
    private final String vendor;
    private final double amount;

    public Transactions(LocalDateTime dateTime, String description, String vendor, double amount) {
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return getDate();
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public String toCsv() {
        return String.format("%s|%s|%s|%.2f", dateTime, description, vendor, amount);
    }
}