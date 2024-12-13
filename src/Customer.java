import java.io.*;
import java.util.*;

public class Customer {

    private List<Account> accounts;
    private String personNumber;
    private String name;
    private int age;

    public Customer(String personNumber, String name, int age) {
        this.personNumber = personNumber;
        this.name = name;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

   public Customer() {
        this.accounts = new ArrayList<>();
    }


    //Behövde flytta hit denna metod från Account för att kunna få den att koppla på rätt sätt
    public  void  addAccount(Account account){
        this.accounts.add(account);
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
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

}