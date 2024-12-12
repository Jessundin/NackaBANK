import java.util.ArrayList;
import java.util.List;

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

    public List<Person> getPerson() {
        return person;
    }

    public static void main(String[] args) {

        while (true) {

            Bank bank = Bank.getInstance();

            Customer customer = new Customer();
            customer.welcomePrompt();
            customer.getWelcomeInputChoice();
        }
    }
}
