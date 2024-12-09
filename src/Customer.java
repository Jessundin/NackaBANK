import java.util.ArrayList;
import java.util.List;

public class Customer extends Person implements BankAccountFunctions {

    private List<Account> accounts;
    private Account account;

    public Customer(String name, int age) {
        super(name, age);
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolder() {
        return super.getName();
    }

    // Getter för kontolistan
    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    @Override
    public void deposit() {
//            for(Account account : accounts){
//
//            }
    }


    @Override
    public void withdraw() {
        double test = account.getBalance();
        //Behövs läggas till att man måste ha tillräckligt med pengar på kontot.
    }

    @Override
    public double getBalance() {
        return account.getBalance();
    }

    @Override
    public void payBill() {
//        if (amount <= balance) {
//            balance -= amount;
//            System.out.println("Räkningen är betalad");
//        } else {
//            System.out.println("Inte tillräckligt med pengar på detta konto");
//        }

        //behövs en override string toString här

    }
}
