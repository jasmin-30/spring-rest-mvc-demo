package com.example.springrestmvcdemo.repositories;

import com.example.springrestmvcdemo.bootstrap.BootStrapData;
import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    void testBeerOrders() {
        System.out.println(customerRepository.count());
        System.out.println(beerOrderRepository.count());
        System.out.println(beerRepository.count());
        System.out.println(testBeer.getBeerName());
        System.out.println(testCustomer.getName());
    }
}