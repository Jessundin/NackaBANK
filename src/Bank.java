import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private List <Person> person;
    private static Bank instance;



    private Bank() {
        this.person = new ArrayList<>();
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
        Account account = new Account();
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


    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public static void main(String[] args) {

        while (true) {

            Bank bank = Bank.getInstance();

            bank.welcomePrompt();
            bank.getWelcomeInputChoice();
        }
    }
}
