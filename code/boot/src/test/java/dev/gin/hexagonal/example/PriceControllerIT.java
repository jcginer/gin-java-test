package dev.gin.hexagonal.example;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.util.TestUtils;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

public class PriceControllerIT extends IntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  @Sql({"/data/remove-prices.sql", "/data/insert-prices.sql"})
  public void get_price_by_parameters() {

    final PriceResponse expectedResponse = PriceResponse.builder()
        .brandId(TestUtils.BRAND_ID).productId(TestUtils.PRODUCT_ID)
        .price(25.45).currency(TestUtils.EUR)
        .startDate("2020-06-14 15:00:00")
        .endDate("2020-06-14 18:30:00")
        .priority(1)
        .build();

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/price")
            .queryParam("brandId", "1")
            .queryParam("productId", "35455")
            .queryParam("pricingDate", "2020-06-14T16:00:00Z")
            .build())
        .exchange()
        .expectStatus().isOk()
        .expectBody(PriceResponse.class)
        .consumeWith(result -> {
          assertThat(Objects.requireNonNull(result.getResponseBody()))
              .isEqualTo(expectedResponse);
        });
  }
}
