package com.technicaltest.backend.domain.port.out;

import com.technicaltest.backend.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Output port for Price repository.
 * Defines the contract for price data access without any framework dependency.
 */
public interface PriceRepositoryPort {

    /**
     * Finds all prices applicable for a given product, brand and date.
     *
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @param applicationDate the date to check applicability
     * @return list of applicable prices, empty if none found
     */
    List<Price> findApplicablePrices(Long productId, Long brandId, LocalDateTime applicationDate);
}