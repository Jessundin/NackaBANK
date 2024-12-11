public class Account implements BankComponent {
    private Customer customer;
    private double balance = 0;

    public Account(String personNumber, String name, int age) {
        customer = new Customer();
        customer.setPersonNumber(personNumber);
        customer.setName(name);
        customer.setAge(age);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        }
    }

    @Override
    public void display() {
        System.out.println("Konto för: " + customer.getName());
        System.out.println("Saldo: " + balance + " kr");
    }
}