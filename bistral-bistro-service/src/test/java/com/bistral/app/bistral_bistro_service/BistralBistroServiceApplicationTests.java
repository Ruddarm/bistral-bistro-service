package com.bistral.app.bistral_bistro_service;

import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.enums.ItemUnit;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantQueryRepository;
import com.bistral.app.bistral_bistro_service.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BistralBistroServiceApplicationTests {


    @Autowired
    private TenantOnboardingService tenantOnboardingService;

    @Test
    void contextLoads() {

    }


    @Test
    public void testingTenantOnboarding(){
        tenantOnboardingService.createTenant(
                TenantOnboardingDto
                        .builder()
                        .tenantName("Abc Test")
                        .tenantSubDomain("abc").build()
        );
    }

}
