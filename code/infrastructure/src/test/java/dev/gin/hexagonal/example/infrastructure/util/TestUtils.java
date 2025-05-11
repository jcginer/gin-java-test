package dev.gin.hexagonal.example.infrastructure.util;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntity;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class TestUtils {

  public static final Long BRAND_ID = 1L;
  public static final Long PRODUCT_ID = 100L;
  public static final Double PRICE_AMOUNT = 35.50;
  public static final String EUR = "EUR";
  public static final String START_DATE = "2020-06-14T00:00:00Z";
  public static final String END_DATE = "2020-12-31T23:59:59Z";
  public static final String PRICING_DATE = "2020-12-31T23:59:59";
  public static final int PRIORITY = 0;

  public static Price getDefaultPrice(final UUID priceId) {
    return new Price(Optional.ofNullable(priceId).orElse(UUID.randomUUID()), BRAND_ID, PRODUCT_ID,
        PRICE_AMOUNT, EUR, Instant.parse(START_DATE), Instant.parse(END_DATE), PRIORITY);
  }

  public static PriceEntity getDefaultPriceEntity(final UUID priceId) {
    return new PriceEntity(Optional.ofNullable(priceId).orElse(UUID.randomUUID()), BRAND_ID,
        PRODUCT_ID, PRICE_AMOUNT, EUR,
        Instant.parse(START_DATE), Instant.parse(END_DATE), PRIORITY);
  }

  public static PriceEntity getPriceWithPriorityEntity(final UUID priceId, final Integer priority) {
    return new PriceEntity(Optional.ofNullable(priceId).orElse(UUID.randomUUID()), BRAND_ID,
        PRODUCT_ID, PRICE_AMOUNT, EUR,
        Instant.parse(START_DATE), Instant.parse(END_DATE), priority);
  }

}
