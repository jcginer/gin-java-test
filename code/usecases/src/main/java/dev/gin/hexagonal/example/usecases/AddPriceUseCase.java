package dev.gin.hexagonal.example.usecases;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.domain.service.PriceCacheEvictService;
import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.usecases.dto.CreatePriceRequestCaseDto;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import dev.gin.hexagonal.example.usecases.mapper.CreatePriceRequestCaseDtoMapper;
import dev.gin.hexagonal.example.usecases.mapper.PriceResponseCaseDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class AddPriceUseCase {
  // TODO: This is class is added to show/test the cache -> Some additional implementation could
  //  be missing, and no tests have been implemented, so all that have to be done in Next steps.

  private final CreatePriceRequestCaseDtoMapper requestMapper;
  private final PriceResponseCaseDtoMapper mapper;
  private final PriceRepository repository;
  private final PriceCacheEvictService priceCacheEvictService;

  @Transactional(rollbackFor = Exception.class)
  public PriceResponseCaseDto execute(@Valid final CreatePriceRequestCaseDto priceRequestCaseDto)
      throws EntityNotFoundException {
    final Price storedPrice = repository.createPrice(requestMapper.map(priceRequestCaseDto));
    priceCacheEvictService.evictPricesCache(storedPrice.brandId(), storedPrice.productId(),
        storedPrice.startDate(), storedPrice.endDate());
    return mapper.map(storedPrice);
  }
}
