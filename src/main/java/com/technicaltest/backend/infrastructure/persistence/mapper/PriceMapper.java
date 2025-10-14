package com.technicaltest.backend.infrastructure.persistence.mapper;

import com.technicaltest.backend.domain.model.Price;
import com.technicaltest.backend.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between PriceEntity and Price domain model.
 * Generated at compile time by MapStruct annotation processor.
 */
@Mapper(componentModel = "spring")
public interface PriceMapper {

    /**
     * Converts PriceEntity (JPA) to Price (Domain).
     *
     * @param entity JPA entity
     * @return domain model
     */
    Price toDomain(PriceEntity entity);

    /**
     * Converts Price (Domain) to PriceEntity (JPA).
     *
     * @param domain domain model
     * @return JPA entity
     */
    @Mapping(target = "id", ignore = true)
    PriceEntity toEntity(Price domain);
}