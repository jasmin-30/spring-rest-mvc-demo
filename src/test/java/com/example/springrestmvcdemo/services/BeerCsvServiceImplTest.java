package com.example.springrestmvcdemo.services;

import com.example.springrestmvcdemo.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void testConvertCSV() throws FileNotFoundException {
        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> beerCSVRecords = beerCsvService.convertCSV(csvFile);
        System.out.println(beerCSVRecords.size());

        assertThat(beerCSVRecords.size()).isGreaterThan(0);
    }
}