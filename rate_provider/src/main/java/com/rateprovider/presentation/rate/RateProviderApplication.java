package com.rateprovider.presentation.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RateProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateProviderApplication.class, args);
    }

}
