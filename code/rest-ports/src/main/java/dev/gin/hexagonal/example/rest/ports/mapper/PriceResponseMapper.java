package dev.gin.hexagonal.example.rest.ports.mapper;

import dev.gin.hexagonal.example.rest.ports.api.model.PriceResponse;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface PriceResponseMapper {

  @Mapping(target = "price", source = "priceAmount")
  PriceResponse map(final PriceResponseCaseDto priceResponseCaseDto);
}
