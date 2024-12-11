public interface BankComponent {
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount);
    void display(); // För att visa information
}

// ett gemensamt interface