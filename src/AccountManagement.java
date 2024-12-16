import java.io.IOException;
import java.util.Scanner;

public class AccountManagement implements BankAccountFunctions {

    private Customer customer;
    FileManagement fileManagement;
    Scanner sc = new Scanner(System.in);

    private double balance = 0;
    private double amount = -1;
    private static final String CUSTOMER_TEXT_FILE = "customers.txt";
    private static final String TRANSACTIONS_TEXT_FILE = "Transactions.txt";

    public AccountManagement(Customer customer) {
        this.customer = customer;
        this.fileManagement = new FileManagement(customer);
        this.fileManagement.setAccountManagement(this);
    }

    @Override
    public double checkBalance(Customer customer) {
        if (balance >= 0) {
            System.out.println("Current balance: " + balance + " kr");
            return balance;
        } else {
            System.out.println("No active account selected");
            return 0.0;
        }
    }

    @Override
    public void deposit() {

        while (amount <= 0) {
            System.out.print("Enter amount to deposit: ");

            if (sc.hasNextDouble()) {
                amount = sc.nextDouble();
                sc.nextLine();

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
            fileManagement.updateBalanceInFile(CUSTOMER_TEXT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("New balance: " + balance + " kr");
        amount = -1;
    }

    @Override
    public void withdraw() {

        while (amount <= 0 || amount > balance) {
            System.out.print("Enter amount to withdraw: ");

            if (sc.hasNextDouble()) {
                amount = sc.nextDouble();
                sc.nextLine();

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
            fileManagement.updateBalanceInFile(CUSTOMER_TEXT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully withdrew " + amount + " kr");
        System.out.println("New balance: " + balance + " kr");
        amount = -1;
    }

    @Override
    public void payBill() {

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
            balance -= amount;

            try {
                fileManagement.writeToFile(TRANSACTIONS_TEXT_FILE, null, 0, null, amount, recipient, customer);

            } catch (IOException e) {
                System.out.println("Error saving transaction history");
            }

            try {
                fileManagement.updateBalanceInFile(CUSTOMER_TEXT_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Payment successful!");
            System.out.println("New balance: " + balance + " kr");
            
        } else {
            System.out.println("Payment cancelled");
        }

    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
