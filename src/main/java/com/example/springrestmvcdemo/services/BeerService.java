package com.example.springrestmvcdemo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.springrestmvcdemo.model.BeerDTO;

public interface BeerService {
    List<BeerDTO> listBeers();
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveNewBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);
    Boolean deleteBeerById(UUID beerId);
    void patchBeerById(UUID beerId, BeerDTO beer);
}
