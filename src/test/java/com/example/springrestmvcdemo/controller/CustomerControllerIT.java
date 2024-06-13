package com.example.springrestmvcdemo.controller;

import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.exception.NotFoundException;
import com.example.springrestmvcdemo.model.CustomerDTO;
import com.example.springrestmvcdemo.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        customerRepository.deleteAll();
        assertThat(customerController.listCustomers().size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }
}