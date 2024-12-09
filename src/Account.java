public class Account {
    private String id;
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


    public void deposit(double amount) { // Insättning
    }


    public void withdraw(double amount) {// Uttag
    }

    // Betala faktura
    public void payBill(String ocr, double amount) {
    }
//
//    //@Override
//    public String toString() {
//        //return
//    }
}
