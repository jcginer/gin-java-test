package dev.gin.hexagonal.example.domain.exception;

public class EntityPersistenceException extends RuntimeException {

  public EntityPersistenceException(String message) {
    super(message);
  }
}
