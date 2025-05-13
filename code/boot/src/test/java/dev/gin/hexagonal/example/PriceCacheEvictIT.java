package dev.gin.hexagonal.example;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.usecases.AddPriceUseCase;
import dev.gin.hexagonal.example.usecases.GetPriceByParametersUseCase;
import dev.gin.hexagonal.example.usecases.dto.CreatePriceRequestCaseDto;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import dev.gin.hexagonal.example.util.TestUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;

public class PriceCacheEvictIT extends IntegrationTest {

  public static final String PRICING_DATE = "2020-06-14T16:00:00";

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private GetPriceByParametersUseCase getPriceByParametersUseCase;

  @Autowired
  private AddPriceUseCase addPriceUseCase;

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
    cache.clear();
    final String expectedKey = TestUtils.BRAND_ID + TestUtils.CACHE_SEPARATOR +
        TestUtils.PRODUCT_ID + TestUtils.CACHE_SEPARATOR +
        LocalDateTime.parse(PRICING_DATE).atZone(ZoneId.systemDefault()).toInstant();

    // When
    PriceResponseCaseDto findByParametersCaseDtoResult = getPriceByParametersUseCase.execute(
        TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        LocalDateTime.parse(PRICING_DATE).atZone(ZoneId.systemDefault()).toInstant());

    // Then
    assertThat(findByParametersCaseDtoResult).isNotNull();

    assertThat(cache).isNotNull();
    final Price cachedValue = cache.get(expectedKey, Price.class);
    assertThat(cachedValue).isNotNull();
    assertThat(findByParametersCaseDtoResult.brandId()).isEqualTo(cachedValue.brandId());
    assertThat(findByParametersCaseDtoResult.productId()).isEqualTo(cachedValue.productId());
    assertThat(findByParametersCaseDtoResult.priceAmount()).isEqualTo(cachedValue.priceAmount());
    assertThat(findByParametersCaseDtoResult.currency()).isEqualTo(cachedValue.currency());
    assertThat(findByParametersCaseDtoResult.startDate()).isEqualTo(cachedValue.startDate());
    assertThat(findByParametersCaseDtoResult.endDate()).isEqualTo(cachedValue.endDate());
    assertThat(findByParametersCaseDtoResult.priority()).isEqualTo(cachedValue.priority());

    // Given
    final CreatePriceRequestCaseDto givenPriceRequestCaseDto =
        new CreatePriceRequestCaseDto(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
            30.27, "EUR",
            LocalDateTime.parse("2020-06-14T15:55:00")
                .atZone(ZoneId.systemDefault()).toInstant(),
            LocalDateTime.parse("2020-06-14T16:05:00")
                .atZone(ZoneId.systemDefault()).toInstant(),
            2);

    // When
    final PriceResponseCaseDto addedCaseDtoResult =
        addPriceUseCase.execute(givenPriceRequestCaseDto);

    // Then
    assertThat(addedCaseDtoResult).isNotNull();
    final Price expectedNullcachedValue = cache.get(expectedKey, Price.class);
    assertThat(expectedNullcachedValue).isNull();

    // When
    final PriceResponseCaseDto updatedCaseDtoResult = getPriceByParametersUseCase.execute(
        TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        LocalDateTime.parse(PRICING_DATE).atZone(ZoneId.systemDefault()).toInstant());

    // Then
    assertThat(updatedCaseDtoResult).isNotNull();

    assertThat(cache).isNotNull();
    final Price cachedValueFinal = cache.get(expectedKey, Price.class);
    assertThat(cachedValueFinal).isNotNull();
    assertThat(addedCaseDtoResult.brandId()).isEqualTo(cachedValueFinal.brandId());
    assertThat(addedCaseDtoResult.productId()).isEqualTo(
        cachedValueFinal.productId());
    assertThat(addedCaseDtoResult.priceAmount()).isEqualTo(
        cachedValueFinal.priceAmount());
    assertThat(addedCaseDtoResult.currency()).isEqualTo(cachedValueFinal.currency());
    assertThat(addedCaseDtoResult.startDate()).isEqualTo(
        cachedValueFinal.startDate());
    assertThat(addedCaseDtoResult.endDate()).isEqualTo(cachedValueFinal.endDate());
    assertThat(addedCaseDtoResult.priority()).isEqualTo(cachedValueFinal.priority());
  }
}
