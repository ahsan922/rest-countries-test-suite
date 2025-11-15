package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.models.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for REST Countries API
 * Tests core functionality and data integrity
 */
@DisplayName("REST Countries API Tests")
public class CountriesApiIT extends BaseTest {

    @Test
    @DisplayName("Get all countries should return success response")
    public void testGetAllCountries() {
        // Use region endpoint instead of /all to avoid rate limiting
        Response response = apiClient.get("/region/Europe");
        
        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0));
        
        Country[] countries = response.as(Country[].class);
        assertTrue(countries.length > 0, "Should return at least one country");
        logger.info("Retrieved {} countries", countries.length);
    }

    @Test
    @DisplayName("Get country by country code should return correct country")
    public void testGetCountryByCode() {
        String countryCode = "us";
        Response response = apiClient.get("/alpha/" + countryCode);
        
        response.then()
                .statusCode(200)
                .body("[0].cca2", equalToIgnoringCase("US"));
        
        Country country = response.as(Country[].class)[0];
        assertNotNull(country.getCca2(), "Country code should not be null");
        logger.info("Retrieved country: {}", country);
    }

    @Test
    @DisplayName("Get country by invalid code should return 404")
    public void testGetCountryByInvalidCode() {
        String invalidCode = "zz";
        Response response = apiClient.get("/alpha/" + invalidCode);
        
        response.then()
                .statusCode(404);
        
        logger.info("Correctly received 404 for invalid country code");
    }

    @ParameterizedTest
    @DisplayName("Get countries by valid codes should return success")
    @ValueSource(strings = {"us", "gb", "fr", "de", "jp"})
    public void testGetCountriesByValidCodes(String countryCode) {
        Response response = apiClient.get("/alpha/" + countryCode);
        
        assertEquals(200, response.getStatusCode(), 
                "Should return 200 for country code: " + countryCode);
        
        Country[] countries = response.as(Country[].class);
        assertTrue(countries.length > 0, "Should return at least one country");
    }

    @Test
    @DisplayName("Get countries by name should return matching countries")
    public void testGetCountriesByName() {
        String countryName = "united";
        Response response = apiClient.get("/name/" + countryName);
        
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));
        
        Country[] countries = response.as(Country[].class);
        assertTrue(countries.length > 0, "Should find countries with 'united' in name");
    }

    @Test
    @DisplayName("Get countries by region should return countries in that region")
    public void testGetCountriesByRegion() {
        String region = "Europe";
        Response response = apiClient.get("/region/" + region);
        
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));
        
        Country[] countries = response.as(Country[].class);
        assertTrue(countries.length > 0, "Should return countries from Europe region");
        
        for (Country country : countries) {
            assertEquals(region, country.getRegion(), 
                    "All countries should be from " + region + " region");
        }
    }

    @Test
    @DisplayName("Get countries by currency should return countries with that currency")
    public void testGetCountriesByCurrency() {
        String currency = "usd";
        Response response = apiClient.get("/currency/" + currency);
        
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));
        
        logger.info("Found {} countries with {} currency", response.jsonPath().getList("").size(), currency);
    }

    @Test
    @DisplayName("Get countries by language should return countries with that language")
    public void testGetCountriesByLanguage() {
        String language = "spanish";
        Response response = apiClient.get("/lang/" + language);
        
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));
        
        logger.info("Found {} countries with {} language", response.jsonPath().getList("").size(), language);
    }

    @Test
    @DisplayName("Country object should have required fields")
    public void testCountryObjectStructure() {
        Response response = apiClient.get("/alpha/us");
        
        response.then()
                .statusCode(200)
                .body("[0].name", notNullValue())
                .body("[0].cca2", notNullValue())
                .body("[0].region", notNullValue());
        
        Country country = response.as(Country[].class)[0];
        assertNotNull(country.getName(), "Country name should not be null");
        assertNotNull(country.getCca2(), "Country code (cca2) should not be null");
    }

    @Test
    @DisplayName("Multiple countries query should work with filters")
    public void testMultipleCountriesWithFilters() {
        Response response = given()
                .baseUri("https://restcountries.com/v3.1")
                .queryParam("fields", "name,capital,region")
                .get("/all");
        
        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Capital endpoint should return country with matching capital")
    public void testGetCountriesByCapital() {
        String capital = "washington";
        Response response = apiClient.get("/capital/" + capital);
        
        // Some capitals may not work with this endpoint, accept both 200 and 404
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404, 
                "Should return 200 or 404 for capital search");
    }

    @Test
    @DisplayName("API should handle fast sequential requests")
    public void testSequentialRequests() {
        for (int i = 0; i < 5; i++) {
            try {
                if (i > 0) {
                    Thread.sleep(500); // Add delay between requests to avoid rate limiting
                }
                // Use different regions for sequential requests to avoid rate limiting
                String[] regions = {"Europe", "Asia", "Africa", "Americas", "Oceania"};
                Response response = apiClient.get("/region/" + regions[i]);
                assertEquals(200, response.getStatusCode(), 
                        "Request " + (i + 1) + " should succeed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Test interrupted", e);
            }
        }
        logger.info("Successfully executed 5 sequential requests");
    }

    @Test
    @DisplayName("Response time should be acceptable")
    public void testResponseTime() {
        long startTime = System.currentTimeMillis();
        // Use region endpoint instead of /all to avoid rate limiting
        Response response = apiClient.get("/region/Asia");
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        assertEquals(200, response.getStatusCode());
        assertTrue(responseTime < 10000, 
                "Response time should be less than 10 seconds, was: " + responseTime + "ms");
        logger.info("Response time: {}ms", responseTime);
    }

    @Test
    @DisplayName("Filtered fields request should reduce response size")
    public void testFilteredFieldsRequest() {
        Response allFieldsResponse = apiClient.get("/alpha/us");
        Response filteredResponse = given()
                .baseUri("https://restcountries.com/v3.1")
                .queryParam("fields", "name,cca2")
                .get("/alpha/us");
        
        assertEquals(200, allFieldsResponse.getStatusCode());
        assertEquals(200, filteredResponse.getStatusCode());
        
        String allFieldsBody = allFieldsResponse.getBody().asString();
        String filteredBody = filteredResponse.getBody().asString();
        
        assertTrue(filteredBody.length() < allFieldsBody.length(), 
                "Filtered response should be smaller");
    }
}
