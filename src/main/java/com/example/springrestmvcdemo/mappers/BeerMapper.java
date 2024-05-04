package com.example.springrestmvcdemo.mappers;

import com.example.springrestmvcdemo.entities.Beer;
import com.example.springrestmvcdemo.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
