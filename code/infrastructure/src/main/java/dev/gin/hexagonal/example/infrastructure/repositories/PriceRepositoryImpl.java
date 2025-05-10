package dev.gin.hexagonal.example.infrastructure.repositories;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.domain.exception.EntityPersistenceException;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntity;
import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntityMapper;
import dev.gin.hexagonal.example.infrastructure.repositories.jpa.PriceJpaRepository;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * The type Price repository.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class PriceRepositoryImpl implements PriceRepository {

  private final PriceJpaRepository repository;
  private final PriceEntityMapper mapper;

  // TODO: Add @Cache
  @Override
  public Price findByParameters(@NotNull Long brandId, @NotNull Long productId,
      @NotNull Instant pricingDate) throws EntityNotFoundException {

    List<PriceEntity> priceEntities = null;

    try {
      // Used LocalDateTime instead of Instant due to the behaviour of H2 database that doesn't work
      // fine work with UTC time. PostgreSQL handles date/time types much more robustly than H2, so
      // in case of using it, it wouldn't be required this conversion
      LocalDateTime localDateTime = LocalDateTime.ofInstant(pricingDate, ZoneOffset.UTC);
      priceEntities = repository.findByParameters(brandId, productId, localDateTime);
    } catch (Throwable e) {
      final String errorMessage = String.format("Error finding price for brandId: %s; "
          + "productId: %s;  pricingDate: %s", brandId, productId, pricingDate);
      log.warn(errorMessage, e);
      throw new EntityPersistenceException(errorMessage);
    }

    return mapper.map(priceEntities.stream()
        .max(Comparator.comparing(PriceEntity::getPriority))
        .orElseThrow(
            () -> new EntityNotFoundException(
                "Product Not found for the required date-time")));
  }

  @Override
  public List<Price> findAll() {
    return repository.findAll().stream().map(mapper::map).toList();
  }

}
