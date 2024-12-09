
public class Account {

    private final String id;
    private double balance;

    public Account(String id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;

    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double balance){
        return this.balance += balance;
    }

    // Representerar objektets tillstånd. Alltså dem värden som ska skrivas ut
    // Exempelvis. Balance, uppdaterad balance efter utag osv...
    /*@Override
    public String toString() {
        return ;
    }


     */
    @Override
    public String toString() {
        return "Account ID: " + id + ", Balance: " + balance;
    }
}
