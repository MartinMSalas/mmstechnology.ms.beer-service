package com.mmstechnology.ms.beer_service.web.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

    UUID uuid;

    private String beerName;
    private BeerStyleEnum beerStyle;
    private Long upc;
    private BigDecimal price;

    private Integer quantityOnHand;

    @CreatedDate
    @Column(updatable = false, name= "create_dt")
    private OffsetDateTime createdDate;
    @LastModifiedDate
    @Column(name= "update_dt")
    private OffsetDateTime lastModifiedDate;



}
