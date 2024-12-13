import java.util.*;

public class Customer {

    private final List<Account> accounts;
    private String socialSecurityNumber;
    private String name;
    private int age;

    public Customer(String socialSecurityNumber, String name, int age) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public Customer() {
        this.accounts = new ArrayList<>();
    }

    //Behövde flytta hit denna metod från Account för att kunna få den att koppla på rätt sätt
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}