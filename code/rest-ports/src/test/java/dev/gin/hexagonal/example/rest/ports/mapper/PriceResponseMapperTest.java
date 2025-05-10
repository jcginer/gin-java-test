package dev.gin.hexagonal.example.rest.ports.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.rest.ports.exception.util.TestUtils;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceResponseMapperTest {

  final PriceResponseMapper mapper = Mappers.getMapper(PriceResponseMapper.class);

  @Test
  void map_successful() {
    // Given
    final PriceResponseCaseDto givenObject = TestUtils.getDefaultPriceResponseCaseDto();
    final PriceResponse expectedResponse = TestUtils.getDefaultPriceResponse();

    // When
    final PriceResponse response = mapper.map(givenObject);

    // Then
    assertThat(response).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResponse);
  }
}