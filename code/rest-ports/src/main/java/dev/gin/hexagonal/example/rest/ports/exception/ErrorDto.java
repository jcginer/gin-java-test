package dev.gin.hexagonal.example.rest.ports.exception;

public record ErrorDto(
    Integer code,
    String title,
    String description
) {

}