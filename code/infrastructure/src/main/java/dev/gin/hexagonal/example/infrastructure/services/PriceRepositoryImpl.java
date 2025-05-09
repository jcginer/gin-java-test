package dev.gin.hexagonal.example.infrastructure.services;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Price repository.
 */
@Slf4j
@Component
public class PriceRepositoryImpl implements PriceRepository {

  @Override
  public Price findByParameters(Long brandId, Long productId,
      Instant pricingDate) {
    return null;
  }
}
