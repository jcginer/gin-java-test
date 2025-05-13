package dev.gin.hexagonal.example.rest.ports.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.rest.ports.exception.mapper.AdviceExceptionMapper;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ExtendWith(MockitoExtension.class)
class PriceControllerAdviceTest {

  public static final String ERROR_MESSAGE = "Error message";

  @InjectMocks
  PriceControllerAdvice instance;
  @Mock
  AdviceExceptionMapper mapper;

  @Test
  void handle_entity_not_found_exception() {
    // Given
    EntityNotFoundException exception = new EntityNotFoundException(ERROR_MESSAGE, new Throwable());
    ErrorDto errorDto = new ErrorDto(null, ERROR_MESSAGE, ERROR_MESSAGE);
    given(mapper.map(exception)).willReturn(errorDto);

    // When
    ResponseEntity<?> response = instance.handleException(exception);

    // Then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode())
        .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    assertThat(response.getBody()).isEqualTo(errorDto);
  }

  @Test
  void handle_constraint_violation_Exception() {
    // Given
    ConstraintViolationException constraintViolationException = new ConstraintViolationException(
        ERROR_MESSAGE, new HashSet<>());

    ErrorDto errorDto = new ErrorDto(null, ERROR_MESSAGE, ERROR_MESSAGE);
    given(mapper.map(any(), any())).willReturn(errorDto);

    // When
    final ResponseEntity<ErrorDto> response = instance.handleException(
        constraintViolationException);

    // Then
    verify(mapper).map(any(), any());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo(errorDto);
  }

  @Test
  void handle_method_argument_not_Valid_exception() {
    // Given
    MethodArgumentNotValidException methodArgumentNotValidException =
        new MethodArgumentNotValidException(
            mock(MethodParameter.class), mock(BindingResult.class));
    ErrorDto errorDto = new ErrorDto(null, ERROR_MESSAGE, ERROR_MESSAGE);
    given(mapper.map(any(), any())).willReturn(errorDto);

    // When
    final ResponseEntity<ErrorDto> response = instance.handleException(
        methodArgumentNotValidException);

    // Then
    verify(mapper).map(any(), any());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo(errorDto);
  }

  @Test
  void handle_method_argument_type_mismatch_exception() {
    // Given
    MethodArgumentTypeMismatchException exception =
        new MethodArgumentTypeMismatchException(null, null, null, null, null);

    ErrorDto errorDto = new ErrorDto(null, ERROR_MESSAGE, ERROR_MESSAGE);
    given(mapper.map(exception)).willReturn(errorDto);

    // When
    ResponseEntity<?> response = instance.handleException(exception);

    // Then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode())
        .isEqualTo(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    assertThat(response.getBody()).isEqualTo(errorDto);
  }

  @Test
  void handle_general_exception() {
    Exception exception = new Exception(ERROR_MESSAGE);

    ErrorDto errorDto = new ErrorDto(null, ERROR_MESSAGE, ERROR_MESSAGE);
    given(mapper.map(exception)).willReturn(errorDto);

    ResponseEntity<?> response = instance.handleException(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode())
        .isEqualTo(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    assertThat(response.getBody()).isEqualTo(errorDto);
  }
}