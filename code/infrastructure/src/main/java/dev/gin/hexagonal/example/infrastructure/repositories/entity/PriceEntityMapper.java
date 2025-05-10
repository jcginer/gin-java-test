package dev.gin.hexagonal.example.infrastructure.repositories.entity;

import dev.gin.hexagonal.example.domain.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

  PriceEntity map(final Price price);

  Price map(final PriceEntity priceEntity);
}
