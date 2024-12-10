
public class Account {

    Customer customer = new Customer();
//    private static int accountNumber = 1000;
//    private final int accountID;
    private double balance = 0;

    public Account(String personNumber, String name, int age) {
//        this.accountID = accountNumber++;
        customer.setPersonNumber(personNumber);
        customer.setName(name);
        customer.setAge(age);
    }

//    public static int getAccountNumber() {
//        return accountNumber;
//    }

//    public static void setAccountNumber(int accountNumber) {
//        Account.accountNumber = accountNumber;
//    }
//
//    public int getAccountID() {
//        return accountID;
//
//    }


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
}
