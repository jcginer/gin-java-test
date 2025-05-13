package dev.gin.hexagonal.example.infrastructure.repositories.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PRICES", indexes = {
    @Index(name = "idx_brand_product_dates_priority",
        columnList = "BRAND_ID, PRODUCT_ID, START_DATE, END_DATE, PRIORITY"
    )
})
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity {

  // PRICE_LIST has been replaced by ID, as PRICE_LIST can be confusing.
  // Used UUID instead of BigInteger as it is more recommended for microservices (distributed
  // systems)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private UUID id;

  @Column(name = "BRAND_ID", nullable = false)
  private Long brandId;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "PRICE", nullable = false)
  private Double priceAmount;

  @Column(name = "CURR", length = 3, nullable = false,
      columnDefinition = "varchar(3) default 'EUR'")
  private String currency;

  @Column(name = "START_DATE", nullable = false)
  private Instant startDate;

  @Column(name = "END_DATE", nullable = false)
  private Instant endDate;

  @Column(name = "PRIORITY", nullable = false)
  private Integer priority;

  // Some database (as H2) doesn't work fine with @GeneratedValue of UUID,
  // so we ensure we create the ID
  @PrePersist
  public void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
  }
}
