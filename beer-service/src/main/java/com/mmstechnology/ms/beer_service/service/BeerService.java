package com.mmstechnology.ms.beer_service.service;

import java.util.Optional;
import java.util.UUID;

import com.mmstechnology.ms.beer_service.web.model.BeerDto;

public interface BeerService {

    Optional<BeerDto> getBeerById(UUID beerId);

    Optional<BeerDto> saveNewBeer(BeerDto beerDto);

    Optional<BeerDto> updateBeer(UUID beerId, BeerDto beerDto);

    void deleteBeer(UUID beerId);
}
