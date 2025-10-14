package com.technicaltest.backend.application.service;

import com.technicaltest.backend.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("GetApplicablePriceUseCase Integration Tests - 5 Required Test Cases")
class GetApplicablePriceUseCaseIntegrationTest {

    @Autowired
    private GetApplicablePriceUseCase useCase;

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test1_requestAt10On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent(), "Price should be found");
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(1L, price.getPriceList());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.getEndDate());
        assertEquals(new BigDecimal("35.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test2_requestAt16On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent(), "Price should be found");
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(2L, price.getPriceList());
        assertEquals(1, price.getPriority(), "Should select priority 1 over priority 0");
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), price.getEndDate());
        assertEquals(new BigDecimal("25.45"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test3_requestAt21On14thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent(), "Price should be found");
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(1L, price.getPriceList());
        assertEquals(0, price.getPriority());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.getEndDate());
        assertEquals(new BigDecimal("35.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void test4_requestAt10On15thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent(), "Price should be found");
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(3L, price.getPriceList());
        assertEquals(1, price.getPriority(), "Should select priority 1 over priority 0");
        assertEquals(LocalDateTime.of(2020, 6, 15, 0, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 15, 11, 0), price.getEndDate());
        assertEquals(new BigDecimal("30.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void test5_requestAt21On16thForProduct35455Brand1() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent(), "Price should be found");
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals(4L, price.getPriceList());
        assertEquals(1, price.getPriority(), "Should select priority 1 over priority 0");
        assertEquals(LocalDateTime.of(2020, 6, 15, 16, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.getEndDate());
        assertEquals(new BigDecimal("38.95"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    @DisplayName("Should return empty when product does not exist")
    void shouldReturnEmptyWhenProductDoesNotExist() {
        // Given
        Long productId = 99999L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertFalse(result.isPresent(), "Should return empty for non-existent product");
    }

    @Test
    @DisplayName("Should return empty when brand does not exist")
    void shouldReturnEmptyWhenBrandDoesNotExist() {
        // Given
        Long productId = 35455L;
        Long brandId = 99L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertFalse(result.isPresent(), "Should return empty for non-existent brand");
    }
}