package com.example.springrestmvcdemo.bootstrap;

import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.entities.Customer;
import com.example.springrestmvcdemo.model.BeerCSVRecord;
import com.example.springrestmvcdemo.model.BeerDTO;
import com.example.springrestmvcdemo.model.BeerStyle;
import com.example.springrestmvcdemo.model.CustomerDTO;
import com.example.springrestmvcdemo.repositories.BeerRepository;
import com.example.springrestmvcdemo.repositories.CustomerRepository;
import com.example.springrestmvcdemo.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;

    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0)
            loadBeerData();

        if (customerRepository.count() == 0)
            loadCustomerData();

        if (beerRepository.count() < 10)
            loadCsvData();
    }

    private void loadCsvData() throws FileNotFoundException {
        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> beerCSVRecords = beerCsvService.convertCSV(csvFile);

        beerCSVRecords.forEach(beerCSVRecord -> {
            BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                case "American Pale Lager" -> BeerStyle.LAGER;
                case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                        BeerStyle.ALE;
                case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                case "American Porter" -> BeerStyle.PORTER;
                case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                case "English Pale Ale" -> BeerStyle.PALE_ALE;
                default -> BeerStyle.PILSNER;
            };

            beerRepository.save(Beer.builder()
                            .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                            .beerStyle(beerStyle)
                            .price(BigDecimal.TEN)
                            .upc(beerCSVRecord.getRow().toString())
                            .quantityOnHand(beerCSVRecord.getCount())
                    .build());

        });

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
