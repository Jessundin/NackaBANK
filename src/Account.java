import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account implements BankAccountFunctions {

    //Koppla allt till objektet customer
    Customer customer;
    private double balance = 0;
    private double amount = -1;
    private static final String CUSTOMER_TEXT_FILE = "customers.txt";
    private static final String TRANSACTIONS_TEXT_FILE = "Transactions.txt";

    public Account(String socialSecurityNumber, String name, int age) {
        customer = new Customer(socialSecurityNumber, name, age);
    }

    public double setBalance(double balance) {
        return this.balance += balance;
    }

    @Override
    public double getBalance() {
        if (balance >= 0) {
            System.out.println("Current balance: " + balance + " kr");
            return balance;
        } else {
            System.out.println("No active account selected");
            return 0.0;
        }
    }

    public void deposit() {
        Scanner sc = new Scanner(System.in);

        while (amount <= 0) {
            System.out.print("Enter amount to deposit: ");

            if (sc.hasNextDouble()) {
                amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println("Amount must be positive. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        }
        balance += amount;
        try {
            updateBalanceInFile(CUSTOMER_TEXT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("New balance: " + balance + " kr");
    }

    public void withdraw() {
        Scanner sc = new Scanner(System.in);
        double amount = -1;

        while (amount <= 0 || amount > balance) {
            System.out.print("Enter amount to withdraw: ");

            if (sc.hasNextDouble()) {
                amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println("Amount must be positive. Please try again.");
                } else if (amount > balance) {
                    System.out.println("Insufficient funds. Please enter a valid amount.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                sc.next();
            }
        }
        balance -= amount;
        try {
            updateBalanceInFile(CUSTOMER_TEXT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully withdrew " + amount + " kr");
        System.out.println("New balance: " + balance + " kr");
    }

    public void writeToFile(String fileName, String customerName, int customerAge, String socialSecurityNumber, double balance, String recipient) throws IOException {
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (fileName.equals(CUSTOMER_TEXT_FILE)) {
                writer.write(customerName + ", " + customerAge + ", " + socialSecurityNumber + ", " + balance + " kr");
                writer.newLine();
            }
            if (fileName.equals(TRANSACTIONS_TEXT_FILE)) {
                LocalDateTime currentDateAndTime = java.time.LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy HH:mm");
                String dateAndTime = currentDateAndTime.format(formatter);
                writer.write(customer.getSocialSecurityNumber() + ", Betalning till " + recipient +
                        ", " + String.format("%.2f", balance) + " kr, " + dateAndTime);
                writer.newLine();
            }
        }
    }

    public void updateBalanceInFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains(customer.getSocialSecurityNumber())) { //om d matchar pnr alltså,
                    lines.add(customer.getName() + ", " + customer.getAge() + ", " + customer.getSocialSecurityNumber() + ", " + balance + " kr");
                } else {
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public void payBill() {
        Scanner sc = new Scanner(System.in);


        System.out.println("\n--- Bill Payment ---");
        System.out.println("Enter recipient name: ");
        String recipient = sc.nextLine();

        System.out.print("Enter bill amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount most be positive ");
            return;
        }
        if (amount > balance) { // OM INTE de finns pengar på kontot
            System.out.println("Your balance is too low for this payment");
            System.out.println("Current balance: " + balance + " kr");
            return;
        }
        System.out.println("\nPayment Details: ");
        System.out.println("Recipient: " + recipient);
        System.out.println("Amount: " + amount + "kr");
        System.out.println("CONFIRM PAYMENT YES/NO:");
        sc.nextLine();
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            //pengarna ska dras från kontot genom att skicka in ett negativt belopp,
            balance -= amount;

            try {
                writeToFile(TRANSACTIONS_TEXT_FILE, null, 0, null, amount, recipient);

            } catch (IOException e) {
                System.out.println("Error saving transaction history"); //fel meddelande om de ej går igenom
            }
            // BEKRÄFTELSE ANG ÖVERFÖRINGEN
            try {
                updateBalanceInFile(CUSTOMER_TEXT_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Payment successful!");
            System.out.println("New balance: " + balance + " kr");
        } else {
            // Om användaren ej tar YES, antingen låter vi den vara så eller så byter vi till goodbye, its doesn't mattaahh
            System.out.println("Payment cancelled");
        }

    }

    public void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter social security number: ");
        customer.setSocialSecurityNumber(sc.next());

        try {
            File file = new File(CUSTOMER_TEXT_FILE);
            if (!file.exists()) {
                System.out.println("No accounts found");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            boolean accountFound = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                //delar upp raderna så de blir name,age,socialSecurityNumber,balance
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[2].equals(customer.getSocialSecurityNumber())) { //om d matchar socialSecurityNumber alltså,
                    customer.setName(parts[0]);
                    customer.setAge(Integer.parseInt(parts[1]));
                    double balance = Double.parseDouble(parts[3].replace(" kr", ""));

                    // Skapar konto och lägg till i listan
                    Account account = new Account(customer.getSocialSecurityNumber(), customer.getName(), customer.getAge());
                    account.setBalance(balance);
                    this.balance = account.balance;

                    System.out.println("Successfully logged in!");
                    accountFound = true;
                    accountPrompt();
                    getAccountInputChoice();

                    break;
                }
            }
            fileScanner.close();

            if (!accountFound) {
                System.out.println("Account not found with social security number: " + customer.getSocialSecurityNumber());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading customer file");
        }
    }

    public void accountPrompt() {
        System.out.println("----------------------------------------");
        System.out.println("Choose what you would like to do: ");
        System.out.println("1: Check balance\n" +
                "2: Deposit money\n" +
                "3: Whitdraw money from account\n" +
                "4: Pay bill\n" +
                "5: Exit ");
        //Lägga till ett val för att skapa ett subkonto?
    }

    public void getAccountInputChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                getBalance();
                accountPrompt();
                getAccountInputChoice();
                break;
            case 2:
                deposit();
                accountPrompt();
                getAccountInputChoice();
                break;

            case 3:
                withdraw();
                accountPrompt();
                getAccountInputChoice();
                break;
            case 4:
                payBill();
                accountPrompt();
                getAccountInputChoice();
                break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);

        }
    }
}