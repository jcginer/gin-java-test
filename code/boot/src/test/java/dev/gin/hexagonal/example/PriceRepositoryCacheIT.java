package dev.gin.hexagonal.example;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.util.TestUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;

public class PriceRepositoryCacheIT extends IntegrationTest {

  public static final String PRICING_DATE = "2020-06-14T16:00:00";

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private PriceRepository priceRepository;

  @BeforeEach
  void clearCache() {
    Cache cache = cacheManager.getCache("pricesCache");
    if (cache != null) {
      cache.clear();
    }
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void test_cache() throws EntityNotFoundException {

    // Given
    final Cache cache = cacheManager.getCache("pricesCache");
    final String expectedKey = TestUtils.BRAND_ID + TestUtils.CACHE_SEPARATOR +
        TestUtils.PRODUCT_ID + TestUtils.CACHE_SEPARATOR +
        LocalDateTime.parse(PRICING_DATE).atZone(ZoneId.systemDefault()).toInstant();

    // When
    final Price firstTimePrice = priceRepository.findByParameters(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID,
        LocalDateTime.parse(PRICING_DATE)
            .atZone(ZoneId.systemDefault()).toInstant());

    // Then
    assertThat(firstTimePrice).isNotNull();

    assertThat(cache).isNotNull();
    final Price cachedValue = cache.get(expectedKey, Price.class);
    assertThat(cachedValue).isNotNull();
    assertThat(firstTimePrice).isEqualTo(cachedValue);
  }
}
