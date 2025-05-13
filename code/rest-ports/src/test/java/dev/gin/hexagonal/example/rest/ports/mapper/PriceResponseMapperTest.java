package dev.gin.hexagonal.example.rest.ports.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.rest.ports.exception.util.TestUtils;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

class PriceResponseMapperTest {

  final PriceResponseMapper mapper = Mappers.getMapper(PriceResponseMapper.class);

  @BeforeEach
  void setup() {
    final CommonMapper commonMapper = Mappers.getMapper(CommonMapper.class);
    ReflectionTestUtils.setField(mapper, "commonMapper", commonMapper);
  }

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