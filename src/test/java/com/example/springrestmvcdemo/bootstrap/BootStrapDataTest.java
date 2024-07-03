package com.example.springrestmvcdemo.bootstrap;

import com.example.springrestmvcdemo.repositories.BeerRepository;
import com.example.springrestmvcdemo.repositories.CustomerRepository;
import com.example.springrestmvcdemo.services.BeerCsvService;
import com.example.springrestmvcdemo.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootStrapDataTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void testRun() throws Exception {
        bootStrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}