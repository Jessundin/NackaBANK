import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Använder Bank.getInstance() för att registrera sig som inloggad kund
//All funktionalitet fungerar som förut!!!!!

public class Customer extends Person implements BankAccountFunctions {
    private List<Account> accounts;
    private Account account;

    public Customer(String personNumber, String name, int age) {
        super(personNumber, name, age);
        this.accounts = new ArrayList<>();
        // Registrera denna kund hos Bank singleton
        Bank.getInstance().setCurrentCustomer(this);
    }

    public Customer() {
        super();
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolderName() {
        return super.getName();
    }

    public int getAccountHolderAge() {
        return super.getAge();
    }

    public String getAccountHolderPersonNumber() {
        return super.getPersonNumber();
    }

    public List<Account> getCustomerAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public void deposit() {
        Scanner sc = new Scanner(System.in);

        if (accounts.isEmpty()) {
            System.out.println("No accounts available");
            return;
        }

        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return;
        }

        if (account != null) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            System.out.println("New balance: " + account.getBalance() + " kr");
        } else {
            System.out.println("No active account selected");
        }
    }

    @Override
    public void withdraw() {
        Scanner sc = new Scanner(System.in);

        if (account == null) {
            System.out.println("No active account selected");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return;
        }

        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds");
            return;
        }

        account.setBalance(-amount);
        System.out.println("Successfully withdrew " + amount + " kr");
        System.out.println("New balance: " + account.getBalance() + " kr");
    }

    @Override
    public double getBalance() {
        if (account != null) {
            double balance = account.getBalance();
            System.out.println("Current balance: " + balance + " kr");
            return balance;
        } else {
            System.out.println("No active account selected");
            return 0.0;
        }
    }

    @Override
    public void payBill() {
        // Implementation for paying bills
        System.out.println("Pay bill functionality coming soon");
    }

    @Override
    public void createAccount() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();

            if (age < 18) {
                System.out.println("You must be at least 18 years old to create an account!\n");
                continue;
            }

            System.out.print("Enter your name: ");
            String name = sc.next();

            System.out.print("Enter your person number: ");
            String pnr = sc.next();

            setName(name);
            setAge(age);
            setPersonNumber(pnr);

            System.out.println("Customer created for " + name + " with person number " + pnr);

            Account account = new Account(pnr, name, age);
            this.addAccount(account);
            this.account = account; // Set as active account

            try {
                writeToFile(name, age, pnr, account.getBalance());
                // Registrera denna Customer som aktiv i Bank singleton
                Bank.getInstance().setCurrentCustomer(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            break;
        }
    }

    private void writeToFile(String name, int age, String pnr, double balance) throws IOException {
        File file = new File("customers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(name + ", " + age + ", " + pnr + ", " + balance + " kr");
            writer.newLine();
        }
    }

    public void accountPromt() {
        System.out.println("----------------------------------------");
        System.out.println("Choose what you would like to do: ");
        System.out.println("1: Check balance\n" +
                "2: Deposit money\n" +
                "3: Withdraw money from account\n" +
                "4: Pay bill\n" +
                "5: Exit");
    }

    public void getAccountInputChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                getBalance();
                accountPromt();
                getAccountInputChoice();
                break;
            case 2:
                deposit();
                accountPromt();
                getAccountInputChoice();
                break;
            case 3:
                withdraw();
                accountPromt();
                getAccountInputChoice();
                break;
            case 4:
                payBill();
                accountPromt();
                getAccountInputChoice();
                break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }
}