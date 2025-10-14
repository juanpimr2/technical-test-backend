package com.technicaltest.backend.domain.port.in;

import com.technicaltest.backend.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Input port for getting applicable price use case.
 * Defines the contract for price query operations.
 */
public interface GetApplicablePricePort {

    /**
     * Gets the applicable price for a product at a given date.
     *
     * @param productId product identifier
     * @param brandId brand identifier
     * @param applicationDate date to check price applicability
     * @return the price with highest priority if found, empty otherwise
     */
    Optional<Price> execute(Long productId, Long brandId, LocalDateTime applicationDate);
}