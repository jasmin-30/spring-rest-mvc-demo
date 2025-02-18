package com.example.springrestmvcdemo.repositories;

import com.example.springrestmvcdemo.bootstrap.BootStrapData;
import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.model.BeerStyle;
import com.example.springrestmvcdemo.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerListByBeerName() {
        Page<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
        assertThat(beerList.getContent().size()).isEqualTo(336);
    }

    @Test
    void testBeerListByBeerStyle() {
        Page<Beer> beerList = beerRepository.findAllByBeerStyle(BeerStyle.IPA, null);
        assertThat(beerList.getContent().size()).isEqualTo(548);
    }

    @Test
    void testBeerListByBeerNameAndBeerStyle() {
        Page<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.IPA, null);
        assertThat(beerList.getContent().size()).isEqualTo(310);
    }

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