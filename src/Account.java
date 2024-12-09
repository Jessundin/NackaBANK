public class Account implements BankAccountFunctions{
    private final String id;
    private double balance;

    public Account(String id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    @Override
    public void deposit(double amount){
            balance += amount;
    }

    @Override
    public void whitdraw(double amount) {

    }

    @Override
    public void withdraw(double amount){
        balance -= amount;
        //Behövs läggas till att man måste ha tillräckligt med pengar på kontot.
    }

    @Override
    public double getBalance(){
        return balance;
    }

    @Override
    public void payBill(String ocr, double amount) {
        if (amount <= balance){
            balance -= amount;
            System.out.println("Räkningen är betalad");
        }
        else {
            System.out.println("Inte tillräckligt med pengar på detta konto");
        }
    }



    // Representerar objektets tillstånd. Alltså dem värden som ska skrivas ut
    // Exempelvis. Balance, uppdaterad balance efter utag osv...
    /*@Override
    public String toString() {
        return ;
    }

     */
}
