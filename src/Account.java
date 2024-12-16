public class Account {

    Customer customer;

    public Account(String socialSecurityNumber, String name, int age) {
        customer = new Customer(socialSecurityNumber, name, age);
    }

    public Customer getCustomer() {
        return customer;
    }
}