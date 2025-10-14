package com.technicaltest.backend.infrastructure.api;

import com.technicaltest.backend.application.dto.PriceResponseDto;
import com.technicaltest.backend.domain.model.Price;
import com.technicaltest.backend.domain.port.in.GetApplicablePricePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * REST Controller for price queries.
 * Provides endpoint to get applicable price for a product at a given date.
 */
@RestController
@RequestMapping("/api/prices")
@Tag(name = "Prices", description = "Price query operations")
public class PriceController {

    private final GetApplicablePricePort getApplicablePricePort;

    public PriceController(GetApplicablePricePort getApplicablePricePort) {
        this.getApplicablePricePort = Objects.requireNonNull(
                getApplicablePricePort,
                "getApplicablePricePort cannot be null"
        );
    }

    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the applicable price for a product in a brand at a specific date. " +
                    "When multiple prices match, returns the one with highest priority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No price found for the given parameters"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameters"
            )
    })
    public ResponseEntity<PriceResponseDto> getApplicablePrice(
            @Parameter(description = "Application date and time (ISO format)", example = "2020-06-14T10:00:00")
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,

            @Parameter(description = "Product identifier", example = "35455")
            @RequestParam("productId")
            Long productId,

            @Parameter(description = "Brand identifier (1 = ZARA)", example = "1")
            @RequestParam("brandId")
            Long brandId
    ) {
        return getApplicablePricePort
                .execute(productId, brandId, applicationDate)
                .map(this::mapToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private PriceResponseDto mapToDto(Price price) {
        return new PriceResponseDto(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }
}