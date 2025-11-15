package com.api.automation.base;

import com.api.automation.client.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test class providing common setup and utilities for all tests
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected ApiClient apiClient;

    @BeforeEach
    public void setUp() {
        apiClient = new ApiClient();
        logger.info("Test setup completed");
    }
}
