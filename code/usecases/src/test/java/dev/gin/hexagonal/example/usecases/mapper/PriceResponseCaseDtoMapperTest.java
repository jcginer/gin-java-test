package dev.gin.hexagonal.example.usecases.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import dev.gin.hexagonal.example.usecases.util.TestUtils;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceResponseCaseDtoMapperTest {

  PriceResponseCaseDtoMapper mapper = Mappers.getMapper(PriceResponseCaseDtoMapper.class);

  @Test
  void map_successful() {
    // Given
    UUID priceId = UUID.randomUUID();
    Price givenObject = TestUtils.getDefaultPrice(priceId);
    PriceResponseCaseDto expectedResult = TestUtils.getDefaultPriceResponseCaseDto();

    // When
    PriceResponseCaseDto resultResponse = mapper.map(givenObject);

    // Then
    assertThat(resultResponse).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResult);
  }
}