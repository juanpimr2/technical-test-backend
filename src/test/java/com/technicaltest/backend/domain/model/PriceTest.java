package com.technicaltest.backend.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Price Domain Entity Tests")
class PriceTest {

    @Test
    @DisplayName("Should create valid price with all required fields")
    void shouldCreateValidPrice() {
        // Given
        Long id = 1L;
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Long priceList = 1L;
        Long productId = 35455L;
        Integer priority = 0;
        BigDecimal price = new BigDecimal("35.50");
        String currency = "EUR";

        // When
        Price result = new Price(id, brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertNotNull(result);
        assertEquals(brandId, result.getBrandId());
        assertEquals(productId, result.getProductId());
        assertEquals(price, result.getPrice());
    }

    @Test
    @DisplayName("Should throw exception when brandId is null")
    void shouldThrowExceptionWhenBrandIdIsNull() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        // When & Then
        assertThrows(NullPointerException.class, () ->
                new Price(1L, null, startDate, endDate, 1L, 35455L, 0,
                        new BigDecimal("35.50"), "EUR")
        );
    }

    @Test
    @DisplayName("Should throw exception when startDate is after endDate")
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 0, 0);

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new Price(1L, 1L, startDate, endDate, 1L, 35455L, 0,
                        new BigDecimal("35.50"), "EUR")
        );
    }

    @Test
    @DisplayName("Should throw exception when price is negative")
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        BigDecimal negativePrice = new BigDecimal("-10.00");

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new Price(1L, 1L, startDate, endDate, 1L, 35455L, 0,
                        negativePrice, "EUR")
        );
    }

    @Test
    @DisplayName("Should throw exception when priority is negative")
    void shouldThrowExceptionWhenPriorityIsNegative() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                new Price(1L, 1L, startDate, endDate, 1L, 35455L, -1,
                        new BigDecimal("35.50"), "EUR")
        );
    }

    @Test
    @DisplayName("Should return true when date is within price range")
    void shouldReturnTrueWhenDateIsWithinRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Price price = new Price(1L, 1L, startDate, endDate, 1L, 35455L, 0,
                new BigDecimal("35.50"), "EUR");
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        // When
        boolean result = price.isApplicableAt(applicationDate);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when date is before price range")
    void shouldReturnFalseWhenDateIsBeforeRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Price price = new Price(1L, 1L, startDate, endDate, 1L, 35455L, 0,
                new BigDecimal("35.50"), "EUR");
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 13, 23, 59);

        // When
        boolean result = price.isApplicableAt(applicationDate);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when date is after price range")
    void shouldReturnFalseWhenDateIsAfterRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Price price = new Price(1L, 1L, startDate, endDate, 1L, 35455L, 0,
                new BigDecimal("35.50"), "EUR");
        LocalDateTime applicationDate = LocalDateTime.of(2021, 1, 1, 0, 0);

        // When
        boolean result = price.isApplicableAt(applicationDate);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when this price has higher priority")
    void shouldReturnTrueWhenPriceHasHigherPriority() {
        // Given
        Price highPriorityPrice = new Price(1L, 1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L, 35455L, 1, new BigDecimal("35.50"), "EUR");

        Price lowPriorityPrice = new Price(2L, 1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                2L, 35455L, 0, new BigDecimal("30.00"), "EUR");

        // When
        boolean result = highPriorityPrice.hasHigherPriorityThan(lowPriorityPrice);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when this price has lower priority")
    void shouldReturnFalseWhenPriceHasLowerPriority() {
        // Given
        Price lowPriorityPrice = new Price(1L, 1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L, 35455L, 0, new BigDecimal("30.00"), "EUR");

        Price highPriorityPrice = new Price(2L, 1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                2L, 35455L, 1, new BigDecimal("35.50"), "EUR");

        // When
        boolean result = lowPriorityPrice.hasHigherPriorityThan(highPriorityPrice);

        // Then
        assertFalse(result);
    }
}