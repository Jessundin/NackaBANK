import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List <Person> person;

    public Bank() {
        this.person = new ArrayList<>();
    }

    public static void main(String[] args) {

        while (true) {
            Customer customer = new Customer();
            customer.welcomePrompt();
            customer.getInputChoice();
        }
    }
}
