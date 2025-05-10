package dev.gin.hexagonal.example.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Price.
 */
public record Price(
    UUID id,
    Long brandId,
    Long productId,
    Double priceAmount,
    String currency,
    Instant startDate,
    Instant endDate,
    Integer priority
) {

}