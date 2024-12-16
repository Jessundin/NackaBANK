import java.util.*;

public class Customer {

    private final List<Account> accounts;
    private List<Customer> customers = new ArrayList<>();
    private String socialSecurityNumber;
    private String name;
    private int age;


    public Customer(String socialSecurityNumber, String name, int age) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}