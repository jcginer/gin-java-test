package dev.gin.hexagonal.example.usecases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * The type Price response case dto.
 */
public record CreatePriceRequestCaseDto(
    @NotNull
    Long brandId,
    @NotNull
    Long productId,
    @NotNull
    Double priceAmount,
    @NotBlank
    String currency,
    @NotNull
    Instant startDate,
    @NotNull
    Instant endDate,
    @NotNull
    Integer priority
) {

}