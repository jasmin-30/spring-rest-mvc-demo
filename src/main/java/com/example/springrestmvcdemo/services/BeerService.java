package com.example.springrestmvcdemo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.springrestmvcdemo.model.BeerDTO;

public interface BeerService {
    List<BeerDTO> listBeers();
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveNewBeer(BeerDTO beer);
    void updateBeerById(UUID beerId, BeerDTO beer);
    void deleteBeerById(UUID beerId);
    void patchBeerById(UUID beerId, BeerDTO beer);
}
