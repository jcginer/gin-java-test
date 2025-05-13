package dev.gin.hexagonal.example.rest.ports.exception;

import dev.gin.hexagonal.example.domain.exception.EntityNotFoundException;
import dev.gin.hexagonal.example.rest.ports.exception.mapper.AdviceExceptionMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class PriceControllerAdvice {

  public static final String INVALID_REQUEST = "Invalid Request";
  private final AdviceExceptionMapper mapper;

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDto> handleException(final EntityNotFoundException e) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapper.map(e));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleException(final ConstraintViolationException exception) {
    log.warn(exception.getMessage());
    String errorsMessage = Optional.of(exception.getConstraintViolations())
        .orElse(Collections.emptySet())
        .stream()
        .map(fieldError ->
            String.format("%s %s",
                Optional.ofNullable(fieldError.getInvalidValue())
                    .map(Object::toString)
                    .filter(StringUtils::isNotEmpty)
                    .orElse(Optional.ofNullable(fieldError.getPropertyPath())
                        .map(Path::toString)
                        .orElse("")),
                fieldError.getMessage()))
        .collect(Collectors.joining(", "));
    final ErrorDto operationMethodArgNotValidErrorDto =
        mapper.map(INVALID_REQUEST, errorsMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(operationMethodArgNotValidErrorDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleException(final MethodArgumentNotValidException exception) {
    String errorsMessage = Optional.of(exception.getBindingResult())
        .map(BindingResult::getFieldErrors)
        .orElse(Collections.emptyList())
        .stream()
        .map(fieldError -> String.format("%s %s",
            fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.joining(", "));
    log.warn(INVALID_REQUEST + " " + errorsMessage);
    final ErrorDto operationMethodArgNotValidErrorDto =
        mapper.map(INVALID_REQUEST, errorsMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(operationMethodArgNotValidErrorDto);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDto> handleException(final MethodArgumentTypeMismatchException e) {
    final String title = INVALID_REQUEST + " Conversion Failed : {}";
    log.warn(title, e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapper.map(e));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(final Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapper.map(e));
  }
}
