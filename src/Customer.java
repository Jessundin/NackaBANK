
import java.util.List;


public class Customer extends Person {
    private String name;
    private int age;
    private List<Account> accounts;

    public Customer(String name, int age, String address) {
        super(name, age, address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

        //behövs en override string toString här

    }
}
