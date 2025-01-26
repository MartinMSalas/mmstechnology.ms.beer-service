package com.mmstechnology.ms.beer_service.web.controller;

import com.mmstechnology.ms.beer_service.exception.BeerCantBeCreated;
import com.mmstechnology.ms.beer_service.exception.BeerNotFound;
import com.mmstechnology.ms.beer_service.service.BeerService;
import com.mmstechnology.ms.beer_service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
@Slf4j
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {
        return beerService.getBeerById(beerId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BeerNotFound("Beer not found with id: " + beerId));
    }


    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody BeerDto beerDto) {
        BeerDto beerDtoCreated = beerService.saveNewBeer(beerDto)
                .orElseThrow(() -> new BeerCantBeCreated("Beer can't be created with id: " + beerDto.getId()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + beerDtoCreated.getId().toString());
        return new ResponseEntity<>(beerDtoCreated, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        BeerDto beerDtoUpdated = beerService.updateBeer(beerId, beerDto)
                .orElseThrow(() -> new BeerNotFound("Beer not found with id: " + beerId));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + beerDtoUpdated.getId().toString());
        return new ResponseEntity<>(beerDtoUpdated, headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        log.debug("In controller - Deleting beer with id: " + beerId);
        beerService.deleteBeer(beerId);
    }

}
