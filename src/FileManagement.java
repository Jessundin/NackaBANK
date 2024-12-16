import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManagement {

    private Customer customer;
    private AccountManagement accountManagement;
    private static final String CUSTOMER_TEXT_FILE = "customers.txt";
    private static final String TRANSACTIONS_TEXT_FILE = "Transactions.txt";

    public FileManagement(Customer customer) {
        this.customer = customer;
    }

    public void setAccountManagement(AccountManagement accountManagement) {
        this.accountManagement = accountManagement;
    }

    public void writeToFile(String fileName, String customerName, int customerAge, String socialSecurityNumber, double balance, String recipient, Customer customer) throws IOException {
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (fileName.equals(CUSTOMER_TEXT_FILE)) {
                writer.write(customerName + ", " + customerAge + ", " + socialSecurityNumber + ", " + balance + " kr");
                writer.newLine();
            }
            if (fileName.equals(TRANSACTIONS_TEXT_FILE)) {
                LocalDateTime currentDateAndTime = java.time.LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy HH:mm");
                String dateAndTime = currentDateAndTime.format(formatter);
                writer.write(customer.getSocialSecurityNumber() + ", Betalning till " + recipient +
                             ", " + String.format("%.2f", balance) + " kr, " + dateAndTime);
                writer.newLine();
            }
        }
    }

    public void updateBalanceInFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.contains(this.customer.getSocialSecurityNumber())) { //om d matchar pnr alltså,
                    lines.add(customer.getName() + ", " + customer.getAge() + ", " + customer.getSocialSecurityNumber() + ", " + accountManagement.checkBalance(customer) + " kr");
                } else {
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public boolean ifPersonNumberExists(String ssn) {
        try {
            File file = new File(CUSTOMER_TEXT_FILE);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(ssn)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
            return false;

        } catch (FileNotFoundException e) {
            return false;
        }
    }
}