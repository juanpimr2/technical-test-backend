package com.technicaltest.backend.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for price query response.
 * Immutable data transfer object.
 */
public class PriceResponseDto {

    private final Long productId;
    private final Long brandId;
    private final Long priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;
    private final String currency;

    public PriceResponseDto(Long productId, Long brandId, Long priceList,
                            LocalDateTime startDate, LocalDateTime endDate,
                            BigDecimal price, String currency) {
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.brandId = Objects.requireNonNull(brandId, "brandId cannot be null");
        this.priceList = Objects.requireNonNull(priceList, "priceList cannot be null");
        this.startDate = Objects.requireNonNull(startDate, "startDate cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
    }

    // Getters
    public Long getProductId() { return productId; }
    public Long getBrandId() { return brandId; }
    public Long getPriceList() { return priceList; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;
        PriceResponseDto that = (PriceResponseDto) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(brandId, that.brandId) &&
                Objects.equals(priceList, that.priceList) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(price, that.price) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, brandId, priceList, startDate, endDate, price, currency);
    }

    @Override
    public String toString() {
        return "PriceResponseDto{" +
                "productId=" + productId +
                ", brandId=" + brandId +
                ", priceList=" + priceList +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}