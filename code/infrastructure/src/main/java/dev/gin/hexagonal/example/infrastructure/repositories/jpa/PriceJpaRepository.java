package dev.gin.hexagonal.example.infrastructure.repositories.jpa;

import dev.gin.hexagonal.example.infrastructure.repositories.entity.PriceEntity;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, UUID> {

  @Query(nativeQuery = true, value = """
      SELECT * FROM PRICES 
      WHERE BRAND_ID = :brandId
        AND PRODUCT_ID = :productId
        AND START_DATE <= :pricingDate
        AND :pricingDate <= END_DATE
      ORDER BY PRIORITY DESC
      LIMIT 1
      """)
  Optional<PriceEntity> findByParameters(
      @Param("brandId") final Long brandId,
      @Param("productId") final Long productId,
      @Param("pricingDate") final Instant pricingDate);
}
