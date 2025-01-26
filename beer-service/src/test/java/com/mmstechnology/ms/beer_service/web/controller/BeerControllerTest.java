package com.mmstechnology.ms.beer_service.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmstechnology.ms.beer_service.exception.BeerCantBeCreated;
import com.mmstechnology.ms.beer_service.exception.BeerNotFound;
import com.mmstechnology.ms.beer_service.service.BeerService;
import com.mmstechnology.ms.beer_service.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BeerService beerService;

    private UUID beerId;
    private BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerId = UUID.randomUUID();
        beerDto = BeerDto.builder()
                .id(beerId)
                .beerName("Test Beer")
                .build();
    }

    @Test
    void GetBeerById_WhenBeerExists_ReturnsBeerDetails() throws Exception {
        when(beerService.getBeerById(beerId)).thenReturn(Optional.of(beerDto));

        mockMvc.perform(get("/api/v1/beer/" + beerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(beerId.toString()))
                .andExpect(jsonPath("$.beerName").value("Test Beer"));
    }
    @Test
    void GetBeerById_WhenBeerDoesNotExist_ThrowsBeerNotFound() throws Exception {
        when(beerService.getBeerById(beerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beer/" + beerId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(BeerNotFound.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Beer not found with id: " + beerId, result.getResolvedException().getMessage()));
    }

    @Test
    void SaveNewBeer_WhenBeerIsSaved_ReturnsCreatedStatus() throws Exception {
        when(beerService.saveNewBeer(any(BeerDto.class))).thenReturn(Optional.of(beerDto));

        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"beerName\": \"New Beer\" }"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/beer/" + beerId))
                .andExpect(jsonPath("$.id").value(beerId.toString()));
    }

    @Test
    void SaveNewBeer_WhenBeerCannotBeCreated_ThrowsBeerCantBeCreated() throws Exception {
        when(beerService.saveNewBeer(any(BeerDto.class))).thenThrow(new BeerCantBeCreated("Beer can't be created with id: " + beerDto.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(result -> assertInstanceOf(BeerCantBeCreated.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Beer can't be created with id: " + beerDto.getId(), result.getResolvedException().getMessage()));
    }

    @Test
    void UpdateBeer_WhenBeerIsUpdated_ReturnsAcceptedStatus() throws Exception {
        when(beerService.updateBeer(eq(beerId), any(BeerDto.class))).thenReturn(Optional.of(beerDto));

        mockMvc.perform(put("/api/v1/beer/" + beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"beerName\": \"Updated Beer\" }"))
                .andExpect(status().isAccepted())
                .andExpect(header().string("Location", "/api/v1/beer/" + beerId))
                .andExpect(jsonPath("$.id").value(beerId.toString()));
    }

    @Test
    void UpdateBeer_WhenBeerDoesNotExist_ThrowsBeerNotFound() throws Exception {
        when(beerService.updateBeer(eq(beerId), any(BeerDto.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/beer/" + beerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"beerName\": \"Updated Beer\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void DeleteBeer_WhenBeerExists_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + beerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void DeleteBeer_WhenBeerDoesNotExist_ThrowsBeerNotFound() throws Exception {
        doThrow(new BeerNotFound("Beer not found with id: " + beerId)).when(beerService).deleteBeer(beerId);

        mockMvc.perform(delete("/api/v1/beer/" + beerId))
                .andExpect(status().isNotFound());
    }
}