package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static com.sun.beans.introspect.PropertyInfo.Name.description;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }


    public static void loadTransactions(String fileName) {
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.


        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0], DATE_FORMATTER);
                LocalTime time = LocalTime.parse(tokens[1], TIME_FORMATTER);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);


            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
        System.out.println("enter the date ,in this format(yyyy-MM-dd):");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DATE_FORMATTER);
        System.out.println("enter the time, in this format(HH;mm;ss):");
        String timeInput = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeInput, TIME_FORMATTER);
        System.out.println("Enter the description");
        String description = scanner.nextLine();
        System.out.println("Enter the vendor");
        String vendor = scanner.nextLine();
        System.out.println("enter the amount");

        double amount = Double.parseDouble(scanner.nextLine());
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);

        String transactionString = String.format("%s|%s|%s|%s|%.2f", date.format(DATE_FORMATTER),
                time.format(TIME_FORMATTER), description, vendor, amount);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(transactionString);
            writer.newLine();
            System.out.println("Deposit added successfully");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        System.out.println("enter the date");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DATE_FORMATTER);
        System.out.println("enter the time");
        String timeInput = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeInput, TIME_FORMATTER);
        System.out.println("Enter the description");
        String description = scanner.nextLine();
        System.out.println("enter the vendor");
        String vendor = scanner.nextLine();
        System.out.println("enter the amount");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = amount * -1;
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            String transactionString = new String();
            writer.write(transactionString);
            writer.newLine();
            System.out.println(" payment process successfully");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        System.out.println("Ledger:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        System.out.println("deposits:");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }

        }

    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        System.out.println("payment:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }

        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();


            switch (input) {
                case "1":
                    MonthToDate();
                    break;


                case "2":
                    previousmonth();
                    break;


                case "3":
                    yeartodate();
                    break;

                case "4":
                    previousYear();
                    break;

                case "5":
                    searchByVendor(scanner);
                    break;

                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void searchByVendor(Scanner scanner) {
        System.out.println("Enter the Vendors Name;");
        String vendorName = scanner.nextLine().trim();
        System.out.println("Report for transactions with vendor;" + vendorName);
        for (Transaction transaction: transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendorName)) {
                System.out.println(transaction);
            }

        }

    }

    private static void previousYear() {
        System.out.println("Report for the previous Year");
        LocalDate date = LocalDate.now();

        LocalDate previousYear = date.minusYears(1);
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == previousYear.getYear()) {
                System.out.println(transaction);
            }
        }

    }

    private static void yeartodate() {
        System.out.println("year-to-date report");
        LocalDate date = LocalDate.now();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == date.getYear()) {
                System.out.println(transaction);
            }

        }
    }

    private static void previousmonth() {
        System.out.println("Report for the previous month");
        LocalDate date = LocalDate.now();
        date.minusMonths(1);
 for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonthValue() == date.getMonthValue()){
                if (transaction.getDate().getYear() == date.getYear()) {
                    System.out.println(transaction);
                }
                }
            }


        }


    private static void MonthToDate() {
        System.out.println("month-to-date-report");
        System.out.println("month-to-date report");
        LocalDate date = LocalDate.now();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonthValue() == date.getMonthValue() && transaction.getDate().getYear() == date.getYear()) {
                System.out.println(transaction);
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            if (transactionDate.isEqual(startDate) | transactionDate.isEqual(endDate) | (transactionDate.isAfter(startDate)
                    & transactionDate.isBefore(endDate))) {
                System.out.println("Transaction Date: " + transactionDate);
                System.out.println("Description: " + transaction.getDescription());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("-----------------------------------");
                //Transactions = true;
            }
        }

        //if (!hasTransactionsInRange) {
        System.out.println("No transactions found within the specified date range.");
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.

        System.out.println("Report:");
        for (Transaction transaction : transactions) {
            if(transaction.getVendor().equalsIgnoreCase(vendor)){
                System.out.println(transaction);
            }
        }
    }
}