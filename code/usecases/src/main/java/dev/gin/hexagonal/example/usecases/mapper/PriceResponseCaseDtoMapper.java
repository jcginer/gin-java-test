package dev.gin.hexagonal.example.usecases.mapper;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.usecases.dto.PriceResponseCaseDto;
import org.mapstruct.Mapper;

/**
 * The interface Price response case dto mapper.
 */
@Mapper(componentModel = "spring")
public interface PriceResponseCaseDtoMapper {

  /**
   * Map price response case dto.
   *
   * @param price the price
   * @return the price response case dto
   */
  PriceResponseCaseDto map(final Price price);
}
