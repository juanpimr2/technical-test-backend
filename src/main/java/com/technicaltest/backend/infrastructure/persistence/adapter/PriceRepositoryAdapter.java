package com.technicaltest.backend.infrastructure.persistence.adapter;

import com.technicaltest.backend.domain.model.Price;
import com.technicaltest.backend.domain.port.out.PriceRepositoryPort;
import com.technicaltest.backend.infrastructure.persistence.mapper.PriceMapper;
import com.technicaltest.backend.infrastructure.persistence.repository.PriceJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Adapter that implements the domain port using Spring Data JPA.
 * Bridges infrastructure (JPA) with domain layer.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository jpaRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(PriceJpaRepository jpaRepository, PriceMapper priceMapper) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "jpaRepository cannot be null");
        this.priceMapper = Objects.requireNonNull(priceMapper, "priceMapper cannot be null");
    }

    @Override
    public List<Price> findApplicablePrices(Long productId, Long brandId, LocalDateTime applicationDate) {
        return jpaRepository.findApplicablePrices(productId, brandId, applicationDate)
                .stream()
                .map(priceMapper::toDomain)
                .collect(Collectors.toList());
    }
}