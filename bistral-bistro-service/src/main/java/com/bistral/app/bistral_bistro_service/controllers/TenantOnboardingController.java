package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.ApiMessage;
import com.bistral.app.bistral_bistro_service.dtos.ApiResponse;
import com.bistral.app.bistral_bistro_service.dtos.Response;
import com.bistral.app.bistral_bistro_service.dtos.TenantOnboardingDto;
import com.bistral.app.bistral_bistro_service.enums.responseCode.TenantResponseCode;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceConflictException;
import com.bistral.app.bistral_bistro_service.service.TenantOnboardingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/tenant-onboarding")
public class TenantOnboardingController {

    private final TenantOnboardingService tenantOnboardingService;

    @PostMapping()
    public ResponseEntity<Response<UUID>> createTenant(@RequestBody @Valid  TenantOnboardingDto tenantOnboardingDto) throws ResourceConflictException {
        return ResponseEntity.ok(
                Response.from(tenantOnboardingService.createTenant(tenantOnboardingDto),
                        ApiMessage.of(TenantResponseCode.TENANT_CREATED)
                )
        );
    }


}
