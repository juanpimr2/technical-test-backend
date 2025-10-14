package com.technicaltest.backend.infrastructure.persistence.repository;

import com.technicaltest.backend.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for PriceEntity.
 * Infrastructure layer component.
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds all prices applicable for a product and brand at a specific date.
     * A price is applicable if the application date is within its date range.
     *
     * @param productId product identifier
     * @param brandId brand identifier
     * @param applicationDate date to check applicability
     * @return list of applicable prices
     */
    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId " +
            "AND p.brandId = :brandId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findApplicablePrices(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}