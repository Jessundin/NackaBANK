import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private List<Customer> customers;
    private static Bank instance;


    private Bank() {
        this.customers = new ArrayList<>();
    }

    //detta säkerställer att det följer Singleton då det ändast är en bank som används
    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void welcomePrompt() {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to NackaBANK, choose an option: ");
        System.out.println("1. Create account\n" +
                           "2. Log in\n" +
                           "0. Exit");
    }

    public void getWelcomeInputChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        Customer customer = new Customer();
        Account account = new Account(customer.getSocialSecurityNumber(), customer.getName(), customer.getAge());
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                account.logIn();
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }
    private boolean ifPersonNumberExists(String ssn) {
        try {
            File file = new File("customers.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(ssn)) {  // Kollar bara om personnumret finns i raden, dvs om en sträng innehåller en annan sträng
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
            return false;

        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public void createAccount() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.next();
        String ssn;

        int currentYear = LocalDate.now().getYear();


        while (true) {
            System.out.print("Enter your social security number (10 digits): ");
            ssn = sc.next();

            if (ifPersonNumberExists(ssn)) {
                System.out.println("An account with this social security number already exists!");
                welcomePrompt();  // Visa huvudmenyn igen
                getWelcomeInputChoice();
                return;  // Avbryter hela metoden här
            }

            if (ssn.length() != 10) {
                System.out.println("Please enter a valid 10-digit social security number.");
                continue;
            }

            String firstTwoDigits = ssn.substring(0, 2);

            int birthYear = Integer.parseInt(firstTwoDigits);

            if (birthYear > currentYear % 100) {
                birthYear += 1900;
            } else {
                birthYear += 2000;
            }

            int age = currentYear - birthYear;

            if (age >= 18) {
                break;
            } else {
                System.out.println("You must be at least 18 years old to create an account!\n");
            }
        }

        int age = currentYear - Integer.parseInt(ssn.substring(0, 2));
        Customer customer = new Customer(ssn, name, age);
        Account account = new Account(ssn, name, age);
        customer.addAccount(account);

        this.customers.add(customer);
        System.out.println("Customer created for " + name + " with social security number " + ssn);

        try {
            account.writeToFile("customers.txt", name, age, ssn, account.getBalance(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        while (true) {

            Bank bank = Bank.getInstance();

            bank.welcomePrompt();
            bank.getWelcomeInputChoice();
        }
    }
}
