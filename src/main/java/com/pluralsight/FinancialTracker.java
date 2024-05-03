package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FinancialTracker {
    // File paths for transaction and log files
    private static final String TRANSACTION_FILE = "src/main/resources/transactions.csv";
    private static final String LOG_FILE = "log.txt";

    // Date formatter for logging
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logAction("launch");

        // Main loop to continuously display the home screen
        while (true) {
            displayHomeScreen();
        }
    }

    // Method to display the home screen
    private static void displayHomeScreen() {
        logAction("displayHomeScreen");

        // Display options for the user
        System.out.println("Welcome to the Financial Tracker!");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().toUpperCase(); // Read user input
        System.out.println();

        // Process user choice
        switch (choice) {
            case "D":
                addDeposit(); // Call the method to add a deposit
                break;
            case "P":
                makePayment(); // Call the method to make a payment
                break;
            case "L":
                displayLedger(); // Call the method to display the ledger
                break;
            case "X":
                logAction("exit"); // Log the action as "exit"
                System.out.println("Exiting the application..."); // Print a message indicating application exit
                System.exit(0); // Terminate the application
            default:
                logAction("invalid choice"); // Log the action as "invalid choice"
                System.out.println("Invalid choice. Please enter D, P, L, or X."); // Print a message for invalid input
        }
        System.out.println();
    }

    // Method to add a deposit
    private static void addDeposit() {
        logAction("addDeposit");

        // Prompt user for deposit details
        System.out.println("Enter deposit details:");
        LocalDateTime date = LocalDateTime.now(); // Get current date and time
        System.out.print("Description: ");
        String description = scanner.nextLine(); // Read description from user input
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine(); // Read vendor from user input
        System.out.print("Amount: ");
        double amount = scanner.nextDouble(); // Read amount from user input
        scanner.nextLine(); // Consume newline character

        // Create a transaction object for the deposit
        Transactions deposit = new Transactions(date, description, vendor, amount);
        logAction(deposit); // Log the deposit
        System.out.println("Deposit added successfully!");
    }

    // Method to make a payment
    private static void makePayment() {
        logAction("makePayment");

        // Prompt user for payment details
        System.out.println("Enter payment details:");
        LocalDateTime date = LocalDateTime.now(); // Get current date and time
        System.out.print("Description: ");
        String description = scanner.nextLine(); // Read description from user input
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine(); // Read vendor from user input
        System.out.print("Amount: ");
        double amount = scanner.nextDouble(); // Read amount from user input
        scanner.nextLine(); // Consume newline character

        Transactions payment = new Transactions(date, description, vendor, amount);
        // Create a new Transactions object representing a payment with the provided details

        logAction(payment); // Log the payment action
        System.out.println("Payment made successfully!"); // Notify the user that the payment was successful
    }

    private static void logAction(String action) {
        try {
            FileWriter fw = new FileWriter(LOG_FILE, true);
            BufferedWriter logger = new BufferedWriter(fw);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            logger.write(timestamp + " " + action);
            // Write the timestamp and action to the log file
            logger.newLine(); //adds new line
            // Move to the next line in the log file
            logger.close();
            // Close the BufferedWriter
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an IOException occurs
        }
    }

    private static void logAction(Transactions transaction) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTION_FILE, true))) {
            String formattedAmount = String.format("%.2f", transaction.getAmount());
            // Format the amount to two decimal places
            String logEntry = String.format("%s|%s|%s|%s", transaction.getDateTime().format(formatter),
                    transaction.getDescription(), transaction.getVendor(), formattedAmount);
                    // Create a log entry with transaction details
            writer.println(logEntry);
            // Write the log entry to the transaction file
            System.out.println("Transaction logged successfully: " + logEntry);
            // Notify the user that the transaction was logged successfully
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void displayLedger() {
        logAction("displayLedger");

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            StringBuilder allEntries = new StringBuilder();
            // StringBuilder to store all transaction entries

            while ((line = reader.readLine()) != null) {
                allEntries.append(line).append("\n"); // Appending each line to the end for newest entries first
            }
            // Read each line of the transaction file and append it to the StringBuilder

            System.out.println("Options:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toUpperCase();
            System.out.println();

            String[] entries = allEntries.toString().split("\n");
            // Split the StringBuilder into an array of individual transaction entries

            switch (choice) {
                case "A":
                    displayAllEntries(entries);
                    break;
                case "D":
                    displayDeposits(entries);
                    break;
                case "P":
                    displayPayments(entries);
                    break;
                case "R":
                    displayReports();
                    break;
                case "H":
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            // Process user choice to display ledger options
        } catch (IOException e) {
            System.out.println("Error reading transaction file: " + e.getMessage());
        }
    }


    private static void displayAllEntries(String[] entries) {
        // Method to display deposits from the provided array of transaction entries
        for (String entry : entries) {
            // Iterate through each transaction entry
            System.out.println(entry); // Display all transaction entries
        }
    }


    private static void displayDeposits(String[] entries) {
        // Method to display deposits from the provided array of transaction entries
        for (String entry : entries) {
            // Iterate through each transaction entry
            if (entry.contains("Amazon|")) {
                // Check if the entry contains the keyword "Amazon" (indicating a deposit)
                System.out.println(entry);
                // Display the deposit entry
            }
        }
    }

    private static void displayPayments(String[] entries) {
        // Method to display payments from the provided array of transaction entries
        for (String entry : entries) {
            // Iterate through each transaction entry
            if (entry.contains(" paid|")) {
                // Check if the entry contains the keyword " paid|" (indicating a payment)
                System.out.println(entry);
                // Print the payment entry
            }
        }
    }

    private static void displayReports() {
        // Method to display various financial reports
        logAction("displayReports");
        // Log the action of displaying reports

        System.out.println("Reports:");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
        // Display report options to the user
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        // Read the user's choice of report

        List<Transactions> transactions = List.of();
        // Create an empty list of Transactions to store transaction data for report generation

        switch (choice) {
            case 1:
                generateMonthToDateReport(transactions);
                // Generate and display the Month-To-Date report
                break;
            case 2:
                generatePreviousMonthReport(transactions);
                // Generate and display the Previous Month report
                break;
            case 3:
                generateYearToDateReport(transactions);
                // Generate and display the Year-To-Date report
                break;
            case 4:
                generatePreviousYearReport(transactions);
                // Generate and display the Previous Year report
                break;
            case 5:
                searchByVendor();
                // Allow the user to search transactions by vendor
                break;
            case 0:
                displayLedger();
                // Return to the ledger display
                break;
            default:
                System.out.println("Invalid choice.");
                // Notify the user of an invalid choice
        }
    }

    private static void generateMonthToDateReport(List<Transactions> transactions) {
        // Method to generate and display the Month-To-Date report
        System.out.println("Generating Month-To-Date report...");

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        // Get the first and last days of the current month

        List<Transactions> monthToDateTransactions = transactions.stream()
                .filter(transaction -> isBetween(transaction.getDate(), firstDayOfMonth, lastDayOfMonth))
                .collect(Collectors.toList());
        // Filter transactions to include only those within the current month

        monthToDateTransactions.forEach(transaction -> System.out.println(transaction.getDate() + " - " +
                transaction.getDescription() + ": $" + transaction.getAmount()));
        // Display each transaction in the Month-To-Date report
    }

    private static void generatePreviousMonthReport(List<Transactions> transactions) {
        // Method to generate and display the Previous Month report
        System.out.println("Generating Previous Month report...");

        LocalDate firstDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
        // Get the first and last days of the previous month

        List<Transactions> previousMonthTransactions = transactions.stream()
                .filter(transaction -> isBetween(transaction.getDate(), firstDayOfPreviousMonth, lastDayOfPreviousMonth))
                .collect(Collectors.toList());
        // Filter transactions to include only those within the previous month

        previousMonthTransactions.forEach(transaction -> System.out.println(transaction.getDate() + " - " +
                transaction.getDescription() + ": $" + transaction.getAmount()));
        // Display each transaction in the Previous Month report
    }


    private static void generateYearToDateReport(List<Transactions> transactions) {
        // Method to generate and display the Year-To-Date report
        System.out.println("Generating Year-To-Date report...");

        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate lastDayOfYear = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());
        // Get the first and last days of the current year

        List<Transactions> yearToDateTransactions = transactions.stream()
                .filter(transaction -> isBetween(transaction.getDate(), firstDayOfYear, lastDayOfYear))
                .collect(Collectors.toList());
        // Filter transactions to include only those within the current year

        yearToDateTransactions.forEach(transaction -> System.out.println(transaction.getDate() + " - " +
                transaction.getDescription() + ": $" + transaction.getAmount()));
        // Display each transaction in the Year-To-Date report
    }


    private static void generatePreviousYearReport(List<Transactions> transactions) {
        // Method to generate and display the Previous Year report
        System.out.println("Generating Previous Year report...");

        LocalDate firstDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate lastDayOfPreviousYear = LocalDate.now().minusYears(1).withDayOfYear(LocalDate.now().minusYears(1).lengthOfYear());
        // Get the first and last days of the previous year

        List<Transactions> previousYearTransactions = transactions.stream()
                .filter(transaction -> isBetween(transaction.getDate(), firstDayOfPreviousYear, lastDayOfPreviousYear))
                .collect(Collectors.toList());
        // Filter transactions to include only those within the previous year

        previousYearTransactions.forEach(transaction -> System.out.println(transaction.getDate() + " - " +
                transaction.getDescription() + ": $" + transaction.getAmount()));
        // Display each transaction in the Previous Year report
    }


    private static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        // Method to check if a date falls within a specified range
        return !date.isBefore(start) && !date.isAfter(end);
        // Return true if the date is not before the start date and not after the end date
    }

    private static void searchByVendor() {
        // Method to search transactions by vendor
        logAction("searchByVendor");

        System.out.print("Enter vendor name: ");
        String vendor = scanner.nextLine();
        // Prompt the user to enter the vendor name for search

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Read each line from the transaction file
                if (line.contains(vendor)) {
                    // Check if the line contains the vendor name
                    System.out.println(line);
                    // Print the transaction entry
                }
            }
        } catch(IOException e) { // Catching IOException to handle errors that may occur while reading the transaction file.
            System.out.println("Error reading transaction file: " + e.getMessage()); // Print an error message indicating the issue encountered, including the specific error message obtained from e.getMessage().
        }
    }
}

