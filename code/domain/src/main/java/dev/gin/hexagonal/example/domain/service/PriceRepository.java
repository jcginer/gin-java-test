package dev.gin.hexagonal.example.domain.service;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public interface PriceRepository {

  Price findByParameters(@NotNull final Long brandId, @NotNull final Long productId,
      @NotNull final Instant pricingDate)
      throws EntityNotFoundException;

  List<Price> findAll();
}
