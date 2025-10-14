package com.technicaltest.backend.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    void cacheManagerShouldBeConfigured() {
        assertNotNull(cacheManager);
        assertNotNull(cacheManager.getCache("items"));
    }
}