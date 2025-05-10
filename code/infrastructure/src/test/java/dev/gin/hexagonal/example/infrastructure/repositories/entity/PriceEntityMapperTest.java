package dev.gin.hexagonal.example.infrastructure.repositories.entity;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.infrastructure.util.TestUtils;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceEntityMapperTest {

  PriceEntityMapper mapper = Mappers.getMapper(PriceEntityMapper.class);

  @Test
  void map_to_price_entity_successful() {
    // Given
    UUID priceId = UUID.randomUUID();
    Price givenObject = TestUtils.getDefaultPrice(priceId);
    PriceEntity expectedResult = TestUtils.getDefaultPriceEntity(priceId);

    // When
    PriceEntity resultResponse = mapper.map(givenObject);

    // Then
    assertThat(resultResponse).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResult);
  }

  @Test
  void map_to_price_successful() {
    // Given
    UUID priceId = UUID.randomUUID();
    PriceEntity givenObject = TestUtils.getDefaultPriceEntity(priceId);
    Price expectedResult = TestUtils.getDefaultPrice(priceId);

    // When
    Price resultResponse = mapper.map(givenObject);

    // Then
    assertThat(resultResponse).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResult);
  }
}