package com.example.springrestmvcdemo.bootstrap;

import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.model.BeerDTO;
import com.example.springrestmvcdemo.model.BeerStyle;
import com.example.springrestmvcdemo.model.CustomerDTO;
import com.example.springrestmvcdemo.repositories.BeerRepository;
import com.example.springrestmvcdemo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0)
            loadBeerData();

        if (customerRepository.count() == 0)
            loadCustomerData();
    }

    private void loadCustomerData() {
        customerRepository.save(Customer.builder()
                .name("John")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        customerRepository.save(Customer.builder()
                .name("Josh")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        customerRepository.save(Customer.builder()
                .name("Rod")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
    }

    private void loadBeerData() {
        beerRepository.save(Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        beerRepository.save(Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());

        beerRepository.save(Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
    }
}
