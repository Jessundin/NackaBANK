
public class Account {

    Customer customer = new Customer();
    private double balance = 0;

    public Account(String personNumber, String name, int age) {
        customer.setPersonNumber(personNumber);
        customer.setName(name);
        customer.setAge(age);
    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double balance){
        return this.balance += balance;
    }
}