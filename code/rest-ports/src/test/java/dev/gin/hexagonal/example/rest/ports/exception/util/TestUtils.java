package dev.gin.hexagonal.example.rest.ports.exception.util;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestUtils {

  public static final long BRAND_ID = 1L;
  public static final long PRODUCT_ID = 100L;
  public static final double PRICE_AMOUNT = 35.50;
  public static final String EUR = "EUR";
  public static final String START_DATE = "2020-06-14T00:00:00";
  public static final String END_DATE = "2020-12-31T23:59:59";
  public static final String START_DATE_RESPONSE = "2020-06-14 00:00:00";
  public static final String END_DATE_RESPONSE = "2020-12-31 23:59:59";
  public static final String PRICING_DATE = "2020-06-14T10:00:00Z";
  public static final int PRIORITY = 0;

  public static PriceResponseCaseDto getDefaultPriceResponseCaseDto() {
    final Instant startDate = LocalDateTime.parse(START_DATE).atZone(ZoneId.systemDefault())
        .toInstant();
    final Instant endDate = LocalDateTime.parse(END_DATE).atZone(ZoneId.systemDefault())
        .toInstant();
    return new PriceResponseCaseDto(BRAND_ID, PRODUCT_ID, PRICE_AMOUNT, EUR,
        startDate, endDate, PRIORITY);
  }

  public static PriceResponse getDefaultPriceResponse() {
    return PriceResponse.builder()
        .brandId(TestUtils.BRAND_ID).productId(TestUtils.PRODUCT_ID).price(TestUtils.PRICE_AMOUNT)
        .currency(TestUtils.EUR)
        .startDate(TestUtils.START_DATE_RESPONSE)
        .endDate(TestUtils.END_DATE_RESPONSE)
        .priority(TestUtils.PRIORITY)
        .build();
  }
}
