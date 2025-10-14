package com.technicaltest.backend.application.service;

import com.technicaltest.backend.domain.model.Price;
import com.technicaltest.backend.domain.port.in.GetApplicablePricePort;
import com.technicaltest.backend.domain.port.out.PriceRepositoryPort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Use case for getting the applicable price for a product at a given date.
 * Applies business rule: when multiple prices match, select the one with highest priority.
 */
public class GetApplicablePriceUseCase implements GetApplicablePricePort {

    private final PriceRepositoryPort priceRepository;

    public GetApplicablePriceUseCase(PriceRepositoryPort priceRepository) {
        this.priceRepository = Objects.requireNonNull(priceRepository, "priceRepository cannot be null");
    }

    @Override
    public Optional<Price> execute(Long productId, Long brandId, LocalDateTime applicationDate) {
        validateInput(productId, brandId, applicationDate);

        return priceRepository.findApplicablePrices(productId, brandId, applicationDate)
                .stream()
                .max(Comparator.comparing(Price::getPriority));
    }

    private void validateInput(Long productId, Long brandId, LocalDateTime applicationDate) {
        Objects.requireNonNull(productId, "productId cannot be null");
        Objects.requireNonNull(brandId, "brandId cannot be null");
        Objects.requireNonNull(applicationDate, "applicationDate cannot be null");
    }
}