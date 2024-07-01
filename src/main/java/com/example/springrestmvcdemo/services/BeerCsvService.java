package com.example.springrestmvcdemo.services;

import com.example.springrestmvcdemo.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
