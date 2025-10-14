package com.technicaltest.backend.infrastructure.config;

import com.technicaltest.backend.application.service.GetApplicablePriceUseCase;
import com.technicaltest.backend.domain.port.in.GetApplicablePricePort;
import com.technicaltest.backend.domain.port.out.PriceRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for application layer beans.
 * Wires domain and application layer without polluting them with Spring annotations.
 */
@Configuration
public class BeanConfiguration {

    /**
     * Creates the GetApplicablePricePort bean.
     * Returns the interface to promote loose coupling.
     *
     * @param priceRepositoryPort implementation of the repository port
     * @return configured use case instance as port interface
     */
    @Bean
    public GetApplicablePricePort getApplicablePricePort(PriceRepositoryPort priceRepositoryPort) {
        return new GetApplicablePriceUseCase(priceRepositoryPort);
    }
}