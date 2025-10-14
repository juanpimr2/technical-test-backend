package com.technicaltest.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain entity representing a price for a product in a brand within a date range.
 * Framework-free, immutable, with validation in constructor.
 */
public final class Price {

    private final Long id;
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    public Price(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                 Long priceList, Long productId, Integer priority, BigDecimal price, String currency) {

        this.id = id;
        this.brandId = Objects.requireNonNull(brandId, "brandId cannot be null");
        this.startDate = Objects.requireNonNull(startDate, "startDate cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate cannot be null");
        this.priceList = Objects.requireNonNull(priceList, "priceList cannot be null");
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.priority = Objects.requireNonNull(priority, "priority cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before endDate");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }

        if (priority < 0) {
            throw new IllegalArgumentException("priority cannot be negative");
        }
    }

    public boolean isApplicableAt(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
    }

    public boolean hasHigherPriorityThan(Price other) {
        return this.priority > other.priority;
    }

    // Getters
    public Long getId() { return id; }
    public Long getBrandId() { return brandId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Long getPriceList() { return priceList; }
    public Long getProductId() { return productId; }
    public Integer getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(id, price.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", productId=" + productId +
                ", priceList=" + priceList +
                ", priority=" + priority +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}