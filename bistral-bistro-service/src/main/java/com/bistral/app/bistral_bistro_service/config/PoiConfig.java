package com.bistral.app.bistral_bistro_service.config;

import jakarta.annotation.PostConstruct;
import org.apache.poi.util.IOUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoiConfig {

    @PostConstruct
    public void init() {
        // Increase POI max array size to 200MB
        IOUtils.setByteArrayMaxOverride(200_000_000);
        System.out.println("POI max array size increased to 200MB");
    }
}
