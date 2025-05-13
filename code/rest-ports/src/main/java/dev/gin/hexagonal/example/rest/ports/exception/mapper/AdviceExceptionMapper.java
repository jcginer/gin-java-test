package dev.gin.hexagonal.example.rest.ports.exception.mapper;

import dev.gin.hexagonal.example.rest.ports.exception.ErrorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdviceExceptionMapper {

  @Mapping(target = "code", ignore = true)
  @Mapping(target = "title", source = "message")
  @Mapping(target = "description", source = "message")
  ErrorDto map(final Exception exception);

  @Mapping(target = "code", ignore = true)
  @Mapping(target = "title", source = "title")
  @Mapping(target = "description", source = "description")
  ErrorDto map(final String title, final String description);
}
