package dev.gin.hexagonal.example.rest.ports;

import dev.gin.hexagonal.example.rest.ports.api.PriceApi;
import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.rest.ports.mapper.PriceResponseMapper;
import dev.gin.hexagonal.example.usecases.GetPriceByParametersUseCase;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

  private final GetPriceByParametersUseCase getPriceByParametersUseCase;
  private final PriceResponseMapper priceResponseMapper;

  @Override
  public ResponseEntity<PriceResponse> getProductPrice(OffsetDateTime pricingDate,
      BigDecimal productId, BigDecimal brandId) {
    log.debug("getProductPrice(brandId: {}, productId: {}, pricingDate: {})",
        brandId, productId, brandId);
    return ResponseEntity.ok(
        priceResponseMapper.map(
            getPriceByParametersUseCase.execute(brandId.longValue(), productId.longValue(),
                pricingDate.toInstant())));
  }
}
