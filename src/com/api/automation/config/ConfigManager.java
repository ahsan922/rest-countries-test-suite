package com.api.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for loading and accessing test environment properties
 */
public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "/config.properties";

    static {
        try (InputStream input = ConfigManager.class.getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("api.base.url", "https://restcountries.com/v3.1");
    }

    public static String getEnvironment() {
        return properties.getProperty("environment", "production");
    }

    public static long getRequestTimeout() {
        return Long.parseLong(properties.getProperty("request.timeout.ms", "5000"));
    }

    public static int getMaxRetries() {
        return Integer.parseInt(properties.getProperty("max.retries", "3"));
    }

    public static boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("logging.enabled", "true"));
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
