package dev.gin.hexagonal.example.rest.ports.exception.util;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import java.time.Instant;
import java.time.OffsetDateTime;

public class TestUtils {

  public static final long BRAND_ID = 1L;
  public static final long PRODUCT_ID = 100L;
  public static final double PRICE_AMOUNT = 35.50;
  public static final String EUR = "EUR";
  public static final String START_DATE = "2020-06-14T00:00:00Z";
  public static final String END_DATE = "2020-12-31T23:59:59Z";
  public static final String PRICING_DATE = "2020-06-14T10:00:00Z";
  public static final int PRIORITY = 0;

  public static PriceResponseCaseDto getDefaultPriceResponseCaseDto() {
    return new PriceResponseCaseDto(BRAND_ID, PRODUCT_ID, PRICE_AMOUNT, EUR,
        Instant.parse(START_DATE), Instant.parse(END_DATE), PRIORITY);
  }

  public static PriceResponse getDefaultPriceResponse() {
    return PriceResponse.builder()
        .brandId(TestUtils.BRAND_ID).productId(TestUtils.PRODUCT_ID).price(TestUtils.PRICE_AMOUNT)
        .currency(TestUtils.EUR).startDate(OffsetDateTime.parse(TestUtils.START_DATE))
        .endDate(OffsetDateTime.parse(TestUtils.END_DATE))
        .priority(TestUtils.PRIORITY)
        .build();
  }
}
