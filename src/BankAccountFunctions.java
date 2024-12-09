public interface BankAccountFunctions {

    public void deposit();

    public void withdraw();

    double getBalance();

    public void payBill(String ocr, double amount);

    // Visar detaljerad information om kontot (kan överlappa med toString tror jag)
    public void displayInfo();

}
