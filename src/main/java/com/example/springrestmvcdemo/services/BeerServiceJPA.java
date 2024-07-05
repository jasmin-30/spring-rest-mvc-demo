package com.example.springrestmvcdemo.services;

import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.mappers.BeerMapper;
import com.example.springrestmvcdemo.model.BeerDTO;
import com.example.springrestmvcdemo.model.BeerStyle;
import com.example.springrestmvcdemo.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction order) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy, order);

        Page<Beer> beerList;

        if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = listBeersByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName)) {
            beerList = listBeersByBeerName(beerName, pageRequest);
        } else if (beerStyle != null) {
            beerList = listBeersByBeerStyle(beerStyle, pageRequest);
        } else {
            beerList = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory)
            beerList.forEach(beer -> beer.setQuantityOnHand(null));

        return beerList.map(beerMapper::beerToBeerDto);

//        return beerList.stream()
//                .map(beerMapper::beerToBeerDto)
//                .toList();
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction order) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            queryPageSize = (pageSize > 1000) ? 1000 : pageSize;
        }

        order = (order != null) ? order : Sort.Direction.ASC;
        sortBy = (StringUtils.hasText(sortBy)) ? sortBy : "beerName";

        Sort sort = Sort.by(order, sortBy);

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    public Page<Beer> listBeersByBeerName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    public Page<Beer> listBeersByBeerStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    public Page<Beer> listBeersByBeerNameAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDto) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setPrice(beerDTO.getPrice());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            atomicReference.set(Optional.of(
                    beerMapper.beerToBeerDto(beerRepository.save(foundBeer))
            ));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }

        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beerDTO.getBeerName())) foundBeer.setBeerName(beerDTO.getBeerName());
            if (beerDTO.getBeerStyle() != null) foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            if (StringUtils.hasText(beerDTO.getUpc())) foundBeer.setUpc(beerDTO.getUpc());
            if (beerDTO.getPrice() != null) foundBeer.setPrice(beerDTO.getPrice());
            if (beerDTO.getQuantityOnHand() != null) foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            atomicReference.set(Optional.of(
                    beerMapper.beerToBeerDto(beerRepository.save(foundBeer))
            ));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
