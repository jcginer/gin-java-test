package dev.gin.hexagonal.example.infrastructure.cache;

import dev.gin.hexagonal.example.domain.service.PriceCacheEvictService;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCacheEvictServiceImpl implements PriceCacheEvictService {

  private final CacheManager cacheManager;

  public void evictPricesCache(@NotNull final Long brandId, final @NotNull Long productId,
      final @NotNull Instant startDate, @NotNull final Instant endDate) {

    final Cache pricesCache = cacheManager.getCache("pricesCache");

    if (pricesCache instanceof ConcurrentMapCache concurrentMapCache) {

      Map<Object, Object> nativeCache = concurrentMapCache.getNativeCache();

      List<Object> keysToEvict = nativeCache.keySet().stream()
          .filter(key -> {
            String[] cacheParts = key.toString().split("--");
            if (cacheParts.length != 3) {
              return false;
            }

            try {
              Long brandIdKey = Long.parseLong(cacheParts[0]);
              Long productIdKey = Long.parseLong(cacheParts[1]);
              Instant pricingDateKey = Instant.parse(cacheParts[2]);

              return brandId.equals(brandIdKey) && productId.equals(productIdKey)
                  && pricingDateMatchPriceDates(startDate, endDate, pricingDateKey);
            } catch (Exception e) {
              return false;
            }
          }).toList();

      keysToEvict.forEach(pricesCache::evict);
    }
  }

  private boolean pricingDateMatchPriceDates(final Instant startDate, final Instant endDate,
      Instant pricingDateKey) {
    return isPricingDateBetweenPriceDates(startDate, endDate, pricingDateKey)
        || isPricingDateInTheLimitsOfPriceDates(startDate, endDate, pricingDateKey);
  }

  private boolean isPricingDateBetweenPriceDates(final Instant startDate, final Instant endDate,
      Instant pricingDateKey) {
    return startDate.isBefore(pricingDateKey) && endDate.isAfter(pricingDateKey);
  }

  private boolean isPricingDateInTheLimitsOfPriceDates(final Instant startDate,
      final Instant endDate,
      Instant pricingDateKey) {
    return (startDate.equals(pricingDateKey) && endDate.isAfter(pricingDateKey))
        || (startDate.isBefore(pricingDateKey) && endDate.equals(pricingDateKey));
  }
}
