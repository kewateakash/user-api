package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user.registration")
public class UserRegistrationProperties {
    private String allowedCountry;
    private int minAge;

    public String getAllowedCountry() {
        return allowedCountry;
    }

    public void setAllowedCountry(String allowedCountry) {
        this.allowedCountry = allowedCountry;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
}

