package dev.gin.hexagonal.example.usecases;

import dev.gin.hexagonal.example.domain.service.PriceRepository;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import dev.gin.hexagonal.example.usecases.mapper.PriceResponseCaseDtoMapper;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class GetPriceByParametersUseCase {

  private final PriceResponseCaseDtoMapper mapper;
  private final PriceRepository repository;

  public PriceResponseCaseDto execute(@NotNull final Long brandId, @NotNull final Long productId,
      @NotNull final Instant pricingDate) {
    log.debug("Execute GetPriceByParametersUseCase(brandId: {}, productId: {}, pricingDate: {})",
        brandId, productId, pricingDate);

    return mapper.map(repository.findByParameters(brandId, productId, pricingDate));
  }
}
