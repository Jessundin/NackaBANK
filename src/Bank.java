import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private List<Customer> customers;
    private static Bank instance;


    private Bank() {
        this.customers = new ArrayList<>();
    }

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
        Account account = new Account(customer.getPersonNumber(), customer.getName(), customer.getAge() );
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
    public void createAccount() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.next();

        System.out.print("Enter your person number: ");
        String pnr = sc.next();


        while (true) {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();

            if (age < 18) {
                System.out.println("You must be at least 18 years old to create an account!\n");
                continue;
            }

            Customer customer = new Customer(pnr, name, age);
            Account account = new Account(pnr, name, age);
            customer.addAccount(account);

            this.customers.add(customer);
            System.out.println("Customer created for " + name + " with person number " + pnr);

            try {
                account.writeToFile(name, age, pnr, account.getBalance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            break;
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
