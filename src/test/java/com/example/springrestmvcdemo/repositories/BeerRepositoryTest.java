package com.example.springrestmvcdemo.repositories;

import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.ALE)
                        .price(new BigDecimal("11.99"))
                        .upc("123445")
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.save(Beer.builder()
                    .beerName("My Beer 01234560123456012345601234560123456012345601234560123456012345601234560123456012345601234560123456")
                    .beerStyle(BeerStyle.ALE)
                    .price(new BigDecimal("11.99"))
                    .upc("123445")
                    .build());

            beerRepository.flush();
        });
    }
}