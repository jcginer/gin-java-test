package dev.gin.hexagonal.example.rest.ports.exception.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gin.hexagonal.example.rest.ports.exception.ErrorDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AdviceExceptionMapperTest {

  public static final String ERROR_MESSAGE = "error message";
  public static final String ERROR_TITLE = "Error title";

  AdviceExceptionMapper mapper = Mappers.getMapper(AdviceExceptionMapper.class);

  @Test
  void map_exception_successful() {
    // When
    final ErrorDto errorDto = mapper.map(new Exception(ERROR_MESSAGE));

    // Then
    assertThat(errorDto).isNotNull();
    assertThat(errorDto.code()).isNull();
    assertThat(errorDto.title()).isEqualTo(ERROR_MESSAGE);
    assertThat(errorDto.description()).isEqualTo(ERROR_MESSAGE);
  }

  @Test
  void map_description_successful() {
    // When
    final ErrorDto errorDto = mapper.map(ERROR_TITLE, ERROR_MESSAGE);

    // Then
    assertThat(errorDto).isNotNull();
    assertThat(errorDto.code()).isNull();
    assertThat(errorDto.title()).isEqualTo(ERROR_TITLE);
    assertThat(errorDto.description()).isEqualTo(ERROR_MESSAGE);
  }
}