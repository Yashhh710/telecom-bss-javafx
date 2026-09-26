package com.telecom.service;

import com.telecom.model.Customer;
import com.telecom.repository.DataStore;
import com.telecom.util.ValidationException;
import com.telecom.util.Validator;
import java.util.Comparator;
import java.util.List;

public class CustomerService {
    public Customer add(String name, String email, String phone, String address) throws ValidationException {
        Validator.validateName(name);
        Validator.validateEmail(email);
        Validator.validatePhone(phone);
        if (DataStore.customers.stream().anyMatch(c -> c.getPhone().equals(phone)))
            throw new ValidationException("A customer with this phone number already exists.");

        int id = DataStore.customers.stream().mapToInt(Customer::getCustomerId).max().orElse(0) + 1;
        Customer customer = new Customer(id, name, email, phone, address);
        DataStore.customers.add(customer);
        return customer;
    }

    public void update(Customer customer, String name, String email, String phone, String address) throws ValidationException {
        Validator.validateName(name);
        Validator.validateEmail(email);
        Validator.validatePhone(phone);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
    }

    public void delete(Customer customer) {
        if (customer.getSimCard() != null)
            DataStore.simCustomerMap.remove(customer.getSimCard().getSimNumber());
        DataStore.customers.remove(customer);
    }

    public List<Customer> search(String text) {
        String q = text.toLowerCase();
        return DataStore.customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(q)
                        || c.getPhone().contains(q)
                        || (c.getSimCard() != null && c.getSimCard().getSimNumber().contains(q)))
                .sorted(Comparator.comparing(Customer::getName))
                .toList();
    }
}
