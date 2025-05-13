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
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

  @Override
  @Cacheable(value = "pricesCache", key = "#brandId + '--' + #productId + '--' + "
      + "#pricingDate.toString()")
  public Price findByParameters(@NotNull Long brandId, @NotNull Long productId,
      @NotNull Instant pricingDate) throws EntityNotFoundException {

    Optional<PriceEntity> optionalPriceEntity = Optional.empty();
    try {
      optionalPriceEntity = repository.findByParameters(brandId, productId, pricingDate);
    } catch (Throwable e) {
      final String errorMessage = String.format("Error finding price for brandId: %s; "
          + "productId: %s;  pricingDate: %s", brandId, productId, pricingDate);
      log.warn(errorMessage, e);
      throw new EntityPersistenceException(errorMessage);
    }

    return mapper.map(
        optionalPriceEntity.orElseThrow(() -> new EntityNotFoundException(
            "Product Not found for the required date-time"))
    );
  }

  @Override
  public List<Price> findAll() {
    return repository.findAll().stream().map(mapper::map).toList();
  }

  @Override
  public Price createPrice(Price price) {
    // TODO: This is method is added to show/test the cache -> Some additional implementation could
    //  be missing, and no tests have been implemented, so all that have to be done in Next steps.
    try {
      PriceEntity priceEntity = mapper.map(price);
      priceEntity = repository.save(priceEntity);
      return mapper.map(priceEntity);
    } catch (Throwable e) {
      final String errorMessage = String.format("Error saving price for %s", price);
      log.warn(errorMessage, e);
      throw new EntityPersistenceException(errorMessage);
    }
  }
}
