package dev.gin.hexagonal.example.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.domain.exception.EntityPersistenceException;
import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntity;
import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntityMapper;
import dev.gin.hexagonal.example.infrastructure.repositories.jpa.PriceJpaRepository;
import dev.gin.hexagonal.example.infrastructure.util.TestUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

  public static final Instant PRICING_DATE = LocalDateTime.parse(TestUtils.PRICING_DATE)
      .atZone(ZoneId.systemDefault()).toInstant();

  @InjectMocks
  PriceRepositoryImpl instance;
  @Mock
  PriceJpaRepository jpaRepository;
  @Mock
  PriceEntityMapper mapper;

  @Test
  void find_by_parameters_successful() {
    // Given
    final UUID priceId = UUID.randomUUID();
    final Price expectedResult = TestUtils.getDefaultPrice(priceId);
    final PriceEntity givenPriceEntityWithPriority = TestUtils.getPriceWithPriorityEntity(null, 2);
    when(jpaRepository.findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE))
        .thenReturn(Optional.of(givenPriceEntityWithPriority));
    when(mapper.map(givenPriceEntityWithPriority)).thenReturn(expectedResult);

    // When
    final Price resultResponse = instance.findByParameters(TestUtils.BRAND_ID,
        TestUtils.PRODUCT_ID, PRICING_DATE);

    // Then
    assertThat(resultResponse).usingRecursiveComparison()
        .ignoringAllOverriddenEquals()
        .isEqualTo(expectedResult);
    verify(jpaRepository).findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE);
    verify(mapper).map(givenPriceEntityWithPriority);
  }

  @Test
  void find_by_parameters_no_result() {
    // Given
    when(jpaRepository.findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE))
        .thenReturn(Optional.empty());

    // When

    assertThrows(EntityNotFoundException.class, () -> {
      instance.findByParameters(TestUtils.BRAND_ID,
          TestUtils.PRODUCT_ID, PRICING_DATE);
    });

    // Then
    verify(jpaRepository).findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE);
  }

  @Test
  void find_by_parameters_persistence_exception() {
    // Given
    when(jpaRepository.findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE))
        .thenThrow(new RuntimeException());

    // When

    assertThrows(EntityPersistenceException.class, () -> {
      instance.findByParameters(TestUtils.BRAND_ID,
          TestUtils.PRODUCT_ID, PRICING_DATE);
    });

    // Then
    verify(jpaRepository).findByParameters(TestUtils.BRAND_ID, TestUtils.PRODUCT_ID, PRICING_DATE);
  }

}