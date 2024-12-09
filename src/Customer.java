import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    private List<Account> accounts;

    public Customer(String name, int age) {
        super(name, age);
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolder() {
        return super.getName();
    }

    // Getter för kontolistan
    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    //behövs en override string toString här

}
