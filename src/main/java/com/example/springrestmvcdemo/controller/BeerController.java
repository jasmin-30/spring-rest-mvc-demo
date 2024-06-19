package com.example.springrestmvcdemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import com.example.springrestmvcdemo.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springrestmvcdemo.model.BeerDTO;
import com.example.springrestmvcdemo.services.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updatePatchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID beerId) {
        if (! beerService.deleteBeerById(beerId))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        if (beerService.updateBeerById(beerId, beer).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody BeerDTO beerDto) throws URISyntaxException {
        BeerDTO savedBeer = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/v1/beer/" + savedBeer.getId().toString()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.info("Get Beer by Id in Controller. Id: {}", beerId.toString());
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.error("In exception handler");
        return ResponseEntity.notFound().build();
    }
}
