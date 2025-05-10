package dev.gin.hexagonal.example.rest.ports.mapper;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

  @Mapping(target = "price", source = "priceAmount")
  PriceResponse map(final PriceResponseCaseDto priceResponseCaseDto);

  default OffsetDateTime map(final Instant instant) {
    return Optional.ofNullable(instant)
        .map(date -> date.atOffset(ZoneOffset.UTC))
        .orElse(null);
  }
}
