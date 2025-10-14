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

        validateNotNull(brandId, "brandId");
        validateNotNull(startDate, "startDate");
        validateNotNull(endDate, "endDate");
        validateNotNull(priceList, "priceList");
        validateNotNull(productId, "productId");
        validateNotNull(priority, "priority");
        validateNotNull(price, "price");
        validateNotNull(currency, "currency");

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before endDate");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }

        if (priority < 0) {
            throw new IllegalArgumentException("priority cannot be negative");
        }

        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
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
        if (o == null || getClass() != o.getClass()) return false;
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