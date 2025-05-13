package dev.gin.hexagonal.example.rest.ports;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.domain.exception.EntityPersistenceException;
import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.rest.ports.exception.util.TestUtils;
import dev.gin.hexagonal.example.rest.ports.mapper.PriceResponseMapper;
import dev.gin.hexagonal.example.usecases.GetPriceByParametersUseCase;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  public static final String ERROR_MESSAGE = "Error message";
  @InjectMocks
  PriceController instance;
  @Mock
  GetPriceByParametersUseCase useCase;
  @Mock
  PriceResponseMapper mapper;

  @Test
  void get_product_price_successfully() {
    // Given
    final PriceResponse expectedResult = TestUtils.getDefaultPriceResponse();
    final PriceResponseCaseDto givenCaseDto = TestUtils.getDefaultPriceResponseCaseDto();
    when(useCase.execute(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, Instant.parse(TestUtils.PRICING_DATE))).thenReturn(givenCaseDto);
    when(mapper.map(givenCaseDto)).thenReturn(expectedResult);

    // when
    final ResponseEntity<PriceResponse> resultResponse = instance.getProductPrice(
        OffsetDateTime.parse(TestUtils.PRICING_DATE), BigDecimal.valueOf(TestUtils.PRODUCT_ID),
        BigDecimal.valueOf(TestUtils.BRAND_ID));

    //Then
    verify(useCase).execute(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, Instant.parse(TestUtils.PRICING_DATE));
    verify(mapper).map(givenCaseDto);
    assertThat(resultResponse.getStatusCode()).isSameAs(HttpStatusCode.valueOf(200));
    assertThat(resultResponse.getBody()).isSameAs(expectedResult);
  }

  @Test
  void get_product_price_entity_not_found_exception() {
    // Given
    when(useCase.execute(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        Instant.parse(TestUtils.PRICING_DATE)))
        .thenThrow(new EntityNotFoundException(ERROR_MESSAGE));

    // when
    final Throwable throwable = catchThrowable(() -> instance.getProductPrice(
        OffsetDateTime.parse(TestUtils.PRICING_DATE), BigDecimal.valueOf(TestUtils.PRODUCT_ID),
        BigDecimal.valueOf(TestUtils.BRAND_ID)));

    //Then
    verify(useCase).execute(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, Instant.parse(TestUtils.PRICING_DATE));
    assertThat(throwable).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void get_product_price_entity_persistence_exception() {
    // Given
    when(useCase.execute(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID,
        Instant.parse(TestUtils.PRICING_DATE)))
        .thenThrow(new EntityPersistenceException(ERROR_MESSAGE));

    // when
    final Throwable throwable = catchThrowable(() -> instance.getProductPrice(
        OffsetDateTime.parse(TestUtils.PRICING_DATE), BigDecimal.valueOf(TestUtils.PRODUCT_ID),
        BigDecimal.valueOf(TestUtils.BRAND_ID)));

    //Then
    verify(useCase).execute(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, Instant.parse(TestUtils.PRICING_DATE));
    assertThat(throwable).isInstanceOf(EntityPersistenceException.class);
  }
}