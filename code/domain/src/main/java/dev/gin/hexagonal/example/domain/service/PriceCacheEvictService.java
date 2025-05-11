package dev.gin.hexagonal.example.domain.service;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public interface PriceCacheEvictService {

  void evictPricesCache(@NotNull final Long brandId, final @NotNull Long productId,
      final @NotNull Instant startDate, @NotNull final Instant endDate);
}
