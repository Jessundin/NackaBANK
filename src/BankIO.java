import java.util.Scanner;

public class BankIO {

    AccountIO accountIO = new AccountIO();

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
        switch (choice) {
            case 1:
                accountIO.createAccount();
                break;
            case 2:
                accountIO.logIn();
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }
}
