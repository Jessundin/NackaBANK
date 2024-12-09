import java.io.*;
import java.util.*;

public class Customer extends Person implements BankAccountFunctions {

    private List<Account> accounts;
    private Account account;

    public Customer(String personNumber, String name, int age) {
        super(personNumber, name, age);
        this.accounts = new ArrayList<>();
    }

    public Customer() {
        super();
        this.accounts = new ArrayList<>();
    }

    public String getAccountHolderName() {
        return super.getName();
    }

    public int getAccountHolderAge() {
        return super.getAge();
    }

    public String getAccountHolderPersonNumber(){
        return super.getPersonNumber();
    }


    public List<Account> getCustomerAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

//    public void removeAccount(Account account) {
//        this.accounts.remove(account.getAccountID());
//    }

    @Override
    public void deposit() {
        Scanner sc = new Scanner(System.in);

        // Kontrollera om det finns några konton
        if (accounts.isEmpty()) {
            System.out.println("No accounts available");
            return;
        }

        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return;
        }

        if (account != null) { // Om vi har ett aktivt konto, används det
            double newBalance = account.getBalance() + amount;  // Beräknar nytt saldo
            account.setBalance(newBalance);  // Sätt det nya saldot en gång deposited " + amount + " kr");
            System.out.println("New balance: " + account.getBalance() + " kr");

            //Försökte använda writeToFile för att uppdatera txt filen efter att något hänt med kontot. Men det funkade inte riktigt
            /*try {
                writeToFile(getAccountHolderName(), getAccountHolderAge(), getAccountHolderPersonNumber(), newBalance);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

             */
        } else {
            System.out.println("No active account selected");
        }
    }

    @Override
    public void withdraw() {
        Scanner sc = new Scanner(System.in);

        //om de finns ett aktivt konto,
        if (account == null) {
            System.out.println("No active account selected");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        if (amount <= 0) { //// Kollar att summan är större än noll (d är en säkerhetskontroll, går ej å ta ut om d negativt)
            System.out.println("Amount must be positive");
            return;
        }

        if (amount > account.getBalance()) { //om d finns tillräckligt med para
            System.out.println("Insufficient funds");
            return;
        }

        account.setBalance(-amount);  // Använder negativt belopp för uttag
        System.out.println("Successfully withdrew " + amount + " kr");
        System.out.println("New balance: " + account.getBalance() + " kr");
    }

    @Override
    public double getBalance() {
        if (account != null) {
            double balance = account.getBalance();
            System.out.println("Current balance: " + balance + " kr");
            return balance;
        } else {
            System.out.println("No active account selected");
            return 0.0;
        }
    }


    //@Override
    public void payBill() {
//        if (amount <= balance) {
//            balance -= amount;
//            System.out.println("Räkningen är betalad");
//        } else {
//            System.out.println("Inte tillräckligt med pengar på detta konto");
//        }

        //behövs en override string toString här

    }

    @Override
    public void createAccount() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();

            if (age < 18) {
                System.out.println("You must be at least 18 years old to create an account!\n");
                continue;
            }

            System.out.print("Enter your name: ");
            String name = sc.next();

            System.out.print("Enter your person number: ");
            String pnr = sc.next();

            System.out.println("Customer created for " + name + " with person number " + pnr);

            Account account = new Account(pnr, name, age);
            this.addAccount(account);

//            System.out.println("Account created with ID: " + account.getAccountID());

            try {
                writeToFile(name, age, pnr, account.getBalance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            break;
        }
    }

    private void writeToFile(String name, int age, String pnr, double balance) throws IOException {
        File file = new File("customers.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(name + ", " + age + ", " + pnr + ", " + balance + " kr");
            writer.newLine();
        }
    }

//    public void removeAccount(int id) {
//        System.out.println("Enter account ID");
//        Scanner sc = new Scanner(System.in);
//        int inputID = sc.nextInt();
//        for (Account account : accounts) {
////            if (account.getAccountID() == inputID) {
////                accounts.remove(id);
////                break;
////            } else System.out.println("Account with ID " + inputID + " does not exist");
//        }
//    }

    public void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter person number: ");
        String pnr = sc.next();

        try {
            File file = new File("customers.txt"); //öppnar filen me konto
            if (!file.exists()) {
                System.out.println("No accounts found");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                //delar upp raderna så de blir name,age,pnr,balance
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[2].equals(pnr)) { //om d matchar pnr alltså,
                    String name = parts[0]; //hämtar all kontoinfo
                    int age = Integer.parseInt(parts[1]);
                    double balance = Double.parseDouble(parts[3].replace(" kr", ""));

                    // Skapar konto och lägg till i listan
                    Account account = new Account(pnr, name, age);
                    account.setBalance(balance);
                    this.account = account;
                    this.accounts.add(account);

                    System.out.println("Successfully logged in!");
                    accountPromt();
                    getAccountInputChoice();
                    return;
                }
            }
            fileScanner.close();

            System.out.println("Account not found");
        } catch (FileNotFoundException e) {
            System.out.println("Error reading customer file");
        }
    }

    public void welcomePrompt() {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to NackaBANK, choose an option: ");
        System.out.println("1. Create account\n" +
                           "2. Log in\n" +
                           "0. Exit");
    }

    public void getWelcomeInputChoice() {
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                logIn();
                break;
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    public void accountPromt(){
        System.out.println("----------------------------------------");
        System.out.println("Choose what you would like to do: ");
        System.out.println( "1: Check balance\n" +
                            "2: Deposit money\n" +
                            "3: Whitdraw money from account\n" +
                            "4: Pay bill\n" +
                            "5: Exit ");
                            //Lägga till ett val för att skapa ett subkonto?
    }

    public void getAccountInputChoice(){
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                getBalance();
                accountPromt();
                getAccountInputChoice();
                break;
            case 2:
                deposit();
                accountPromt();
                getAccountInputChoice();
                break;

            case 3:
                withdraw();
                accountPromt();
                getAccountInputChoice();
                break;
            case 4:
                payBill();
                accountPromt();
                getAccountInputChoice();
                break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);

        }
    }
}