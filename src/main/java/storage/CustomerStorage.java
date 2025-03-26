package storage;

import model.Customer;

import java.util.*;

public class CustomerStorage {
    private static final Map<Integer, Customer> customerMap = new HashMap<>();
    private static int currentId = 1;

    public static Customer addCustomer(Customer customer) {
        customer.setId(currentId++);
        customerMap.put(customer.getId(), customer);
        return customer;
    }

    public static List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    public static Customer getCustomerById(int id) {
        return customerMap.get(id);
    }

    public static Customer updateCustomer(int id, Customer updatedCustomer) {
        if (customerMap.containsKey(id)) {
            updatedCustomer.setId(id);
            customerMap.put(id, updatedCustomer);
            return updatedCustomer;
        }
        return null;
    }

    public static boolean deleteCustomer(int id) {
        return customerMap.remove(id) != null;
    }

    public static boolean exists(int id) {
        return customerMap.containsKey(id);
    }
}