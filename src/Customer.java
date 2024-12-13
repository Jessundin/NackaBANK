import java.io.*;
import java.util.*;

public class Customer extends Person{

    private List<Account> accounts;


    public Customer(String personNumber, String name, int age) {
        super(personNumber, name, age);
        this.accounts = new ArrayList<>();
    }

    public Customer() {
        super();
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolderName() {
        return super.getName();
    }

    public int getAccountHolderAge() {
        return super.getAge();
    }

    public String getAccountHolderPersonNumber(){
        return super.getPersonNumber();
    }


    public List<Account> getCustomerAccounts() {
        return accounts;
    }
}