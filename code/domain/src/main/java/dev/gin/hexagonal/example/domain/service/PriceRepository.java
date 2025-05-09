package dev.gin.hexagonal.example.domain.service;

import dev.gin.hexagonal.example.domain.Price;
import java.time.Instant;

public interface PriceRepository {

  Price findByParameters(final Long brandId, final Long productId, final Instant pricingDate);
}
