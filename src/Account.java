
public class Account {
    private double balance = 0;
    private String accountHolder;
    private String personNumber;

    public Account(String personNumber, String name, int age) {
        this.personNumber = personNumber;
        this.accountHolder = name;
    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double amount) {
        return this.balance += amount;
    }
}


//Förenklad, sparar bara kontoinfo
//Behöver inte egen Customer-instans eftersom den alltid används via Customer-klassen