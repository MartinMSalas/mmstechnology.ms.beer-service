package com.mmstechnology.ms.beer_service.service;

import com.mmstechnology.ms.beer_service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        return Optional.empty();
    }

    @Override
    public Optional<BeerDto> saveNewBeer(BeerDto beerDto) {
        return Optional.empty();
    }

    @Override
    public Optional<BeerDto> updateBeer(UUID beerId, BeerDto beerDto) {
        return Optional.empty();
    }

    @Override
    public void deleteBeer(UUID beerId) {

    }
}
