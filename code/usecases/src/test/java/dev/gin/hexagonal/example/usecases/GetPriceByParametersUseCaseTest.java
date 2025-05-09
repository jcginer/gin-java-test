package dev.gin.hexagonal.example.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import dev.gin.hexagonal.example.usecases.mapper.PriceResponseCaseDtoMapper;
import dev.gin.hexagonal.example.usecases.mapper.util.TestUtils;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPriceByParametersUseCaseTest {

  @InjectMocks
  GetPriceByParametersUseCase useCase;
  @Mock
  PriceResponseCaseDtoMapper mapper;
  @Mock
  PriceRepository repository;

  @Test
  void execute_successful() {
    // Given
    PriceResponseCaseDto expectedResult = TestUtils.getDefaultPriceResponseCaseDto();
    Price expectedPrice = TestUtils.getDefaultPrice(null);
    when(repository.findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        Instant.parse(TestUtils.END_DATE))).thenReturn(expectedPrice);
    when(mapper.map(expectedPrice)).thenReturn(expectedResult);

    // When
    PriceResponseCaseDto resultResponse = useCase.execute(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, Instant.parse(TestUtils.END_DATE));

    // Then
    assertThat(resultResponse).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResult);
    verify(repository).findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        Instant.parse(TestUtils.END_DATE));
    verify(mapper).map(expectedPrice);
  }
}
