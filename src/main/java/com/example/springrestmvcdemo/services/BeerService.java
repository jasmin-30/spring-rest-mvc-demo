package com.example.springrestmvcdemo.services;

import java.util.Optional;
import java.util.UUID;

import com.example.springrestmvcdemo.model.BeerDTO;
import com.example.springrestmvcdemo.model.BeerStyle;
import org.springframework.data.domain.Page;

public interface BeerService {
    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveNewBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);
    Boolean deleteBeerById(UUID beerId);
    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
