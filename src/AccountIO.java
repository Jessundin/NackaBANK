import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class AccountIO {

    private static final String CUSTOMER_TEXT_FILE = "customers.txt";
    Scanner sc = new Scanner(System.in);
    Customer customer;
    BankIO bankIO;
    AccountManagement accountManagement;
    FileManagement fileManagement;

    public void accountPrompt() {
        System.out.println("----------------------------------------");
        System.out.println("Choose what you would like to do: ");
        System.out.println("1: Check balance\n" +
                "2: Deposit money\n" +
                "3: Whitdraw money from account\n" +
                "4: Pay bill\n" +
                "5: Exit ");
    }

    public void handleAccountInputChoice() {
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                accountManagement.checkBalance(customer);
                break;
            case 2:
                accountManagement.deposit();
                break;

            case 3:
                accountManagement.withdraw();
                break;
            case 4:
                accountManagement.payBill();
                break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);

        }
        accountPrompt();
        handleAccountInputChoice();
    }

    public void logIn() {

        if (customer == null) {
            customer = new Customer("", "", 0);
        }

        System.out.print("Enter social security number: ");
        customer.setSocialSecurityNumber(sc.next());

        try {
            File file = new File(CUSTOMER_TEXT_FILE);
            if (!file.exists()) {
                System.out.println("No accounts found");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            boolean accountFound = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[2].equals(customer.getSocialSecurityNumber())) { //om d matchar socialSecurityNumber alltså,
                    customer.setName(parts[0]);
                    customer.setAge(Integer.parseInt(parts[1]));
                    double balance = Double.parseDouble(parts[3].replace(" kr", ""));

                    Account account = new Account(customer.getSocialSecurityNumber(), customer.getName(), customer.getAge());
                    accountManagement = new AccountManagement(customer);
                    accountManagement.setBalance(balance);

                    System.out.println("Successfully logged in!");
                    accountFound = true;
                    accountPrompt();
                    handleAccountInputChoice();

                    break;
                }
            }
            fileScanner.close();

            if (!accountFound) {
                System.out.println("Account not found with social security number: " + customer.getSocialSecurityNumber());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading customer file");
        }
    }

    public void createAccount() {
        String socialSecurityNumber;
        int age;
        int currentYear = LocalDate.now().getYear();

        System.out.print("Enter your name: ");
        String name = sc.next();

        while (true) {
            System.out.print("Enter your social security number (10 digits): ");
            socialSecurityNumber = sc.next();
            bankIO = new BankIO();

            if (fileManagement.ifSocialSecurityNumberExists(socialSecurityNumber)) {
                System.out.println("An account with this social security number already exists!");
                bankIO.welcomePrompt();
                bankIO.getWelcomeInputChoice();
                return;
            }

            if (socialSecurityNumber.length() != 10) {
                System.out.println("Please enter a valid 10-digit social security number.");
                continue;
            }

            String firstTwoDigits = socialSecurityNumber.substring(0, 2);

            int birthYear = Integer.parseInt(firstTwoDigits);

            if (birthYear > currentYear % 100) {
                birthYear += 1900;
            } else {
                birthYear += 2000;
            }

            age = currentYear - birthYear;

            if (age >= 18) {
                break;
            } else {
                System.out.println("You must be at least 18 years old to create an account!\n");
            }
        }

        Customer customer = new Customer(socialSecurityNumber, name, age);
        Account account = new Account(socialSecurityNumber, name, age);
        customer.addAccount(account);
        customer.getCustomers().add(customer);
        this.customer = customer;
        System.out.println("Customer created for " + name + " with social security number " + socialSecurityNumber);

        try {
            fileManagement.writeToFile("customers.txt", customer.getName(), customer.getAge(), customer.getSocialSecurityNumber(), accountManagement.checkBalance(customer), null, customer, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public String setNamePrompt() {

        System.out.println("Enter your name: ");
        return sc.nextLine();
    }

    public String setSocialSecurityNumberPrompt() {
        String socialSecurityNumber;
        while (true) {
            System.out.print("Enter your social security number (10 digits): ");
            socialSecurityNumber = sc.next();
            bankIO = new BankIO();

            if (fileManagement.ifSocialSecurityNumberExists(socialSecurityNumber)) {
                System.out.println("An account with this social security number already exists!");
                bankIO.welcomePrompt();
                bankIO.getWelcomeInputChoice();
                return null;
            }

            if (socialSecurityNumber.length() != 10) {
                System.out.println("Please enter a valid 10-digit social security number.");
                continue;
            }
            return socialSecurityNumber;
        }
    }

    public int setAgePrompt(String socialSecurityNumber) {
        int currentYear = LocalDate.now().getYear();
        String firstTwoDigits = socialSecurityNumber.substring(0, 2);
        int birthYear = Integer.parseInt(firstTwoDigits);

        if (birthYear > currentYear % 100) {
            birthYear += 1900;
        } else {
            birthYear += 2000;
        }
        return currentYear - birthYear;
    }

    public boolean checkIfCustomerIsOldEnough(int age){
        if (age >= 18){
            return true;
        } else {
            System.out.println("You must be at least 18 years old to create an account!\n");
        }
        return false;
    }

     */
}
