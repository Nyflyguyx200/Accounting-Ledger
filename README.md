##Financial Tracker

Financial Tracker is a Java application designed to help users manage their finances by tracking deposits, payments, and generating various financial reports. This application allows users to record their transactions, view their transaction history, and generate reports based on different time periods.

##Features

Add deposits: Users can add deposits by entering details such as description, vendor, and amount.
Make payments: Users can make payments by entering details such as description, vendor, and amount.
View ledger: Users can view their transaction history categorized as deposits and payments.
Generate reports: Users can generate reports for the current month, previous month, current year, previous year, and search transactions by vendor.

##Getting Started

Clone this repository to your local machine.
Compile the Java files using your preferred Java compiler.
Run the FinancialTracker.java file to launch the application.

##Example Usage

Run the application.
Choose the desired action from the home screen menu by entering the corresponding letter (e.g., 'D' for adding a deposit).
Follow the prompts to enter transaction details.
View ledger or generate reports as needed.

Screenshot

![Screenshot 2024-05-03 101319](https://github.com/Nyflyguyx200/Accounting-Ledger/assets/114933451/ad1ffc75-8f21-4c77-b443-9a5d0b0373db)
<img width="958" alt="Capstone 1--" src="https://github.com/Nyflyguyx200/Accounting-Ledger/assets/114933451/3b454c96-58b9-4117-a948-e8b3cbb7213a">
<img width="959" alt="Capstone 1-" src="https://github.com/Nyflyguyx200/Accounting-Ledger/assets/114933451/46ec1fe1-5248-4cbe-b625-b32c2ad8fbf2">
<img width="959" alt="Capstone 1" src="https://github.com/Nyflyguyx200/Accounting-Ledger/assets/114933451/a6b0f04c-3f3d-4268-9be7-328ea03c5ae0">


One Piece of Interesting Code
An interesting piece of code in this project is the method to generate the Month-To-Date report.
This method calculates the transactions that occurred within the current month and displays them along with their details and amounts. 
It demonstrates the use of Java's Date-Time API and Stream API to filter and process transaction data efficiently.

private static void generateMonthToDateReport(List<Transactions> transactions) {
    System.out.println("Generating Month-To-Date report...");

    LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
    LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

    List<Transactions> monthToDateTransactions = transactions.stream()
            .filter(transaction -> isBetween(transaction.getDate(), firstDayOfMonth, lastDayOfMonth))
            .collect(Collectors.toList());

    monthToDateTransactions.forEach(transaction -> System.out.println(transaction.getDate() + " - " +
            transaction.getDescription() + ": $" + transaction.getAmount()));
}
