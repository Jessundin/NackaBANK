
public class Bank {

    BankIO bankIO = new BankIO();

    private static Bank instance;

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    private void start() {
        Boolean running = true;

        while (running) {
            bankIO.welcomePrompt();
            bankIO.getWelcomeInputChoice();
        }
    }

    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
        bank.start();
    }
}
