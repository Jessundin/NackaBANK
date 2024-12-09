public interface BankAccountFunctions {

    public void deposit(double amount);

    public void withdraw(double amount);

    double getBalance();

    public void payBill(String ocr, double amount);

}
