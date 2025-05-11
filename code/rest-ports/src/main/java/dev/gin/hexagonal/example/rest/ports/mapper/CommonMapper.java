package dev.gin.hexagonal.example.rest.ports.mapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface CommonMapper {

  default String map(final Instant instant) {
    return Optional.ofNullable(instant)
        .map(date -> date.atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .orElse(null);
  }
}
