package com.Ipaisa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasebuzzConfig {

    @Value("${easebuzz.api.key}")
    private String apiKey;

    @Value("${easebuzz.api.salt}")
    private String apiSalt;

    @Value("${easebuzz.api.url}")
    private String apiUrl;

    // Getters and Setters
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSalt() {
        return apiSalt;
    }

    public void setApiSalt(String apiSalt) {
        this.apiSalt = apiSalt;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
