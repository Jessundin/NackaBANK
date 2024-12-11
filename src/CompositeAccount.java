import java.util.ArrayList;
import java.util.List;
//Denna klass fungerar som en behållare för flera konton, Den behandlar flera konton som ett enda objekt

public class CompositeAccount implements BankComponent {
    private String name;
    private List<BankComponent> accounts; //// Lista som innehåller alla konton

    public CompositeAccount(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    // Metod för att lägga till konton
    public void addAccount(BankComponent account) {
        accounts.add(account);
    }

    // Metod för att ta bort konton
    public void removeAccount(BankComponent account) {
        accounts.remove(account);
    }

    @Override
    public double getBalance() {
        // Räknar ihop saldo från alla konton
        double totalBalance = 0;
        for(BankComponent account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    @Override
    public void deposit(double amount) {
        // Lägger in pengar på första kontot i listan
        if(!accounts.isEmpty()) {
            accounts.get(0).deposit(amount);
        }
    }

    @Override
    public void withdraw(double amount) {
        // Tar ut pengar från första kontot med tillräckligt saldo
        for(BankComponent account : accounts) {
            if(account.getBalance() >= amount) {
                account.withdraw(amount);
                break;
            }
        }
    }

    @Override
    public void display() {
        System.out.println("Kontogrupp: " + name);
        for(BankComponent account : accounts) {
            account.display();
        }
    }
}