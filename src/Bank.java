import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Har nu all huvudlogik för menyer och inloggning
//Håller reda på vem som är inloggad via currentCustomer
//Kan bara skapas via getInstance()

public class Bank {
    private static Bank instance;
    private List<Person> persons;
    private Customer currentCustomer;

    private Bank() {
        this.persons = new ArrayList<>();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer; // Metod för att få nuvarande inloggad kund
    }


    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer; // Metod för att sätta nuvarande kund
    }

    // Main metoden startar allt
    public static void main(String[] args) {
        Bank bank = Bank.getInstance();

        while (true) {
            bank.showWelcomeMenu();
        }
    }

    private void showWelcomeMenu() {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to NackaBANK, choose an option: ");
        System.out.println("1. Create account\n" +
                "2. Log in\n" +
                "0. Exit");

        handleMenuChoice();
    }

    private void handleMenuChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                currentCustomer = new Customer();
                currentCustomer.createAccount();
                break;
            case 2:
                handleLogin();
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    private void handleLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter person number: ");
        String pnr = sc.next();

        try {
            File file = new File("customers.txt");
            if (!file.exists()) {
                System.out.println("No accounts found");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[2].equals(pnr)) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    double balance = Double.parseDouble(parts[3].replace(" kr", ""));

                    currentCustomer = new Customer(pnr, name, age);
                    Account account = new Account(pnr, name, age);
                    account.setBalance(balance);
                    currentCustomer.addAccount(account);

                    System.out.println("Successfully logged in!");
                    currentCustomer.accountPromt();
                    currentCustomer.getAccountInputChoice();
                    return;
                }
            }
            fileScanner.close();
            System.out.println("Account not found");

        } catch (FileNotFoundException e) {
            System.out.println("Error reading customer file");
        }
    }
}