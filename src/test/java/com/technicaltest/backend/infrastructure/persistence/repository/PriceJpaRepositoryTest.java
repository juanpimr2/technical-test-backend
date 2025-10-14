package com.technicaltest.backend.infrastructure.persistence.repository;

import com.technicaltest.backend.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("PriceJpaRepository Integration Tests")
class PriceJpaRepositoryTest {

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    @Test
    @DisplayName("Should load data.sql and have 4 prices in database")
    void shouldLoadDataAndHaveFourPrices() {
        // When
        List<PriceEntity> allPrices = priceJpaRepository.findAll();

        // Then
        assertEquals(4, allPrices.size());
    }

    @Test
    @DisplayName("Test 1: Should find price at 10:00 on day 14 for product 35455 brand 1")
    void test1_shouldFindPriceAt10On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getPriceList());
        assertEquals("35.50", result.get(0).getPrice().toString());
    }

    @Test
    @DisplayName("Test 2: Should find 2 prices at 16:00 on day 14 for product 35455 brand 1")
    void test2_shouldFindTwoPricesAt16On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertEquals(2, result.size());
        // Should contain price list 1 (priority 0) and price list 2 (priority 1)
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(1L)));
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(2L)));
    }

    @Test
    @DisplayName("Test 3: Should find price at 21:00 on day 14 for product 35455 brand 1")
    void test3_shouldFindPriceAt21On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getPriceList());
        assertEquals("35.50", result.get(0).getPrice().toString());
    }

    @Test
    @DisplayName("Test 4: Should find 2 prices at 10:00 on day 15 for product 35455 brand 1")
    void test4_shouldFindTwoPricesAt10On15thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertEquals(2, result.size());
        // Should contain price list 1 (priority 0) and price list 3 (priority 1)
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(1L)));
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(3L)));
    }

    @Test
    @DisplayName("Test 5: Should find 2 prices at 21:00 on day 16 for product 35455 brand 1")
    void test5_shouldFindTwoPricesAt21On16thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertEquals(2, result.size());
        // Should contain price list 1 (priority 0) and price list 4 (priority 1)
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(1L)));
        assertTrue(result.stream().anyMatch(p -> p.getPriceList().equals(4L)));
    }

    @Test
    @DisplayName("Should return empty list when no prices match")
    void shouldReturnEmptyListWhenNoPricesMatch() {
        // Given
        Long productId = 99999L; // Non-existent product
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when date is outside all ranges")
    void shouldReturnEmptyListWhenDateIsOutsideAllRanges() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);

        // When
        List<PriceEntity> result = priceJpaRepository.findApplicablePrices(
                productId, brandId, applicationDate
        );

        // Then
        assertTrue(result.isEmpty());
    }
}