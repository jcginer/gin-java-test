package dev.gin.hexagonal.example.usecases.mapper;

import dev.gin.hexagonal.example.domain.Price;
import dev.gin.hexagonal.example.usecases.dto.CreatePriceRequestCaseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatePriceRequestCaseDtoMapper {

  // TODO: This is class is added to show/test the cache -> No tests have been implemented
  //  , so they have to be done in Next steps
  Price map(CreatePriceRequestCaseDto priceRequestCaseDto);
}
