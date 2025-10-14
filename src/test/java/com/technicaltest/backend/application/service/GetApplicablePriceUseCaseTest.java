package com.technicaltest.backend.application.service;

import com.technicaltest.backend.domain.model.Price;
import com.technicaltest.backend.domain.port.out.PriceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetApplicablePriceUseCase Tests")
class GetApplicablePriceUseCaseTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    private GetApplicablePriceUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetApplicablePriceUseCase(priceRepositoryPort);
    }

    @Test
    @DisplayName("Should throw exception when repository is null")
    void shouldThrowExceptionWhenRepositoryIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, () ->
                new GetApplicablePriceUseCase(null)
        );
    }

    @Test
    @DisplayName("Should throw exception when productId is null")
    void shouldThrowExceptionWhenProductIdIsNull() {
        // Given
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When & Then
        assertThrows(NullPointerException.class, () ->
                useCase.execute(null, brandId, applicationDate)
        );
    }

    @Test
    @DisplayName("Should throw exception when brandId is null")
    void shouldThrowExceptionWhenBrandIdIsNull() {
        // Given
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When & Then
        assertThrows(NullPointerException.class, () ->
                useCase.execute(productId, null, applicationDate)
        );
    }

    @Test
    @DisplayName("Should throw exception when applicationDate is null")
    void shouldThrowExceptionWhenApplicationDateIsNull() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;

        // When & Then
        assertThrows(NullPointerException.class, () ->
                useCase.execute(productId, brandId, null)
        );
    }

    @Test
    @DisplayName("Should return empty when no prices found")
    void shouldReturnEmptyWhenNoPricesFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepositoryPort.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(Collections.emptyList());

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertFalse(result.isPresent());
        verify(priceRepositoryPort, times(1))
                .findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    @DisplayName("Should return the only price when single price found")
    void shouldReturnOnlyPriceWhenSinglePriceFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price expectedPrice = new Price(
                1L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L, productId, 0, new BigDecimal("35.50"), "EUR"
        );

        when(priceRepositoryPort.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(List.of(expectedPrice));

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
        verify(priceRepositoryPort, times(1))
                .findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    @DisplayName("Should return highest priority price when multiple prices found")
    void shouldReturnHighestPriorityPriceWhenMultiplePricesFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price lowPriorityPrice = new Price(
                1L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L, productId, 0, new BigDecimal("35.50"), "EUR"
        );

        Price highPriorityPrice = new Price(
                2L, brandId,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2L, productId, 1, new BigDecimal("25.45"), "EUR"
        );

        when(priceRepositoryPort.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(Arrays.asList(lowPriorityPrice, highPriorityPrice));

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent());
        assertEquals(highPriorityPrice, result.get());
        assertEquals(1, result.get().getPriority());
        assertEquals(new BigDecimal("25.45"), result.get().getPrice());
        verify(priceRepositoryPort, times(1))
                .findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    @DisplayName("Should select price with priority 1 over priority 0")
    void shouldSelectPriorityOneOverPriorityZero() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        Price priority0 = new Price(
                1L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L, productId, 0, new BigDecimal("35.50"), "EUR"
        );

        Price priority1 = new Price(
                2L, brandId,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0),
                3L, productId, 1, new BigDecimal("30.50"), "EUR"
        );

        when(priceRepositoryPort.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(Arrays.asList(priority0, priority1));

        // When
        Optional<Price> result = useCase.execute(productId, brandId, applicationDate);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getPriority());
        assertEquals(new BigDecimal("30.50"), result.get().getPrice());
    }
}