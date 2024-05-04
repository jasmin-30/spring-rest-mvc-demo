package com.example.springrestmvcdemo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.springrestmvcdemo.model.Customer;

public interface CustomerService {
    List<Customer> listCustomers();
    Optional<Customer> getCustomerById(UUID id);
    Customer saveNewCustomer(Customer customer);
    void updateCustomerById(UUID customerId, Customer customer);
    void deleteCustomerById(UUID customerId);
    void patchCustomerById(UUID customerId, Customer customer);
}
