package com.api.automation.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.api.automation.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * REST Client for making HTTP requests to the API
 * Provides a fluent interface for building and executing requests
 */
public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;
    private final long requestTimeout;

    public ApiClient() {
        this.baseUrl = ConfigManager.getBaseUrl();
        this.requestTimeout = ConfigManager.getRequestTimeout();
    }

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.requestTimeout = ConfigManager.getRequestTimeout();
    }

    /**
     * Get a request specification with default settings
     */
    private RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .header("User-Agent", "RestCountriesTestSuite/1.0")
                .log().ifValidationFails();
    }

    /**
     * Execute a GET request
     */
    public Response get(String endpoint) {
        return get(endpoint, null);
    }

    /**
     * Execute a GET request with query parameters
     */
    public Response get(String endpoint, Map<String, String> queryParams) {
        RequestSpecification spec = getRequestSpec();
        
        if (queryParams != null && !queryParams.isEmpty()) {
            spec.queryParams(queryParams);
        }
        
        logger.info("GET request to: {}{}", baseUrl, endpoint);
        return spec.get(endpoint);
    }

    /**
     * Execute a POST request
     */
    public Response post(String endpoint, Object body) {
        logger.info("POST request to: {}{}", baseUrl, endpoint);
        return getRequestSpec()
                .body(body)
                .post(endpoint);
    }

    /**
     * Execute a PUT request
     */
    public Response put(String endpoint, Object body) {
        logger.info("PUT request to: {}{}", baseUrl, endpoint);
        return getRequestSpec()
                .body(body)
                .put(endpoint);
    }

    /**
     * Execute a DELETE request
     */
    public Response delete(String endpoint) {
        logger.info("DELETE request to: {}{}", baseUrl, endpoint);
        return getRequestSpec()
                .delete(endpoint);
    }

    /**
     * Execute a HEAD request
     */
    public Response head(String endpoint) {
        logger.info("HEAD request to: {}{}", baseUrl, endpoint);
        return getRequestSpec()
                .head(endpoint);
    }
}
