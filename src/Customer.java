import java.io.*;
import java.util.*;

public class Customer extends Person implements BankAccountFunctions {

    private List<Account> accounts;
    private Account account;

    public Customer(String personNumber, String name, int age) {
        super(personNumber, name, age);
        this.accounts = new ArrayList<>();
    }

    public Customer() {
        super();
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolder() {
        return super.getName();
    }

    public List<Account> getCustomerAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

//    public void removeAccount(Account account) {
//        this.accounts.remove(account.getAccountID());
//    }

    @Override
    public void deposit() {
//            for(Account account : accounts){
//
//            }
    }


    @Override
    public void withdraw() {
        double test = account.getBalance();
        //Behövs läggas till att man måste ha tillräckligt med pengar på kontot.
    }

    @Override
    public double getBalance() {
        return account.getBalance();
    }

    @Override
    public void payBill() {
//        if (amount <= balance) {
//            balance -= amount;
//            System.out.println("Räkningen är betalad");
//        } else {
//            System.out.println("Inte tillräckligt med pengar på detta konto");
//        }

        //behövs en override string toString här

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

            System.out.println("Customer created for " + name + " with person number " + pnr);

            Account account = new Account(pnr, name, age);
            this.addAccount(account);

//            System.out.println("Account created with ID: " + account.getAccountID());

            try {
                writeToFile(name, age, pnr, account.getBalance());
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

//    public void removeAccount(int id) {
//        System.out.println("Enter account ID");
//        Scanner sc = new Scanner(System.in);
//        int inputID = sc.nextInt();
//        for (Account account : accounts) {
////            if (account.getAccountID() == inputID) {
////                accounts.remove(id);
////                break;
////            } else System.out.println("Account with ID " + inputID + " does not exist");
//        }
//    }

    public void logIn() {
        // skriv in personnummer för att kolla om personen finns i filen
    }

    public void welcomePrompt() {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to NackaBANK, choose an option: ");
        System.out.println("1. Create account\n" +
                           "2. Log in\n" +
                           "0. Exit");
    }

    public void getInputChoice() {
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                logIn();
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }
}