package dev.gin.hexagonal.example;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.util.TestUtils;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class PriceRepositoryIT extends IntegrationTest {

  @Autowired
  private PriceRepository priceRepository;

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_all_prices() {
    // When
    final List<Price> allPrices = priceRepository.findAll();

    // Then
    assertThat(allPrices).isNotNull();
    assertThat(allPrices).hasSize(4);
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_by_parameters_prueba_1() {
    find_by_parameters("2020-06-14T10:00:00Z", "00000000-0000-0000-0000-000000000001");
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_by_parameters_prueba_2() {
    find_by_parameters("2020-06-14T16:00:00Z", "00000000-0000-0000-0000-000000000002");
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_by_parameters_prueba_3() {
    find_by_parameters("2020-06-14T21:00:00Z", "00000000-0000-0000-0000-000000000001");
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_by_parameters_prueba_4() {
    find_by_parameters("2020-06-15T10:00:00Z", "00000000-0000-0000-0000-000000000003");
  }

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void find_by_parameters_prueba_5() {
    find_by_parameters("2020-06-16T21:00:00Z", "00000000-0000-0000-0000-000000000004");
  }

  private void find_by_parameters(final String givenPricingDate, final String expectedId) {
    // When
    final Price result = priceRepository.findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        Instant.parse(givenPricingDate));

    // Then
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(UUID.fromString(expectedId));
  }

}
