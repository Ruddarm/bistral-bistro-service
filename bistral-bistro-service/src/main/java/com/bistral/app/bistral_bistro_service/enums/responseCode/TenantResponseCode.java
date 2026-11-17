package com.bistral.app.bistral_bistro_service.enums.responseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum TenantResponseCode implements ApiResponseCode {
    TENANT_CREATED("TENANT_201", "tenant-created", "Tenant Created", HttpStatus.CREATED, "Tenant Created Successfully", ResponseSeverity.SUCCESS),
    SUBDOMAIN_TAKEN("TENANT_409", "subdomain-taken", "Subdomain is taken", HttpStatus.CONFLICT, "Subdomain is already taken by another tenant", ResponseSeverity.WARN);
    private final String code;
    private final String slug;
    private final String title;
    private final HttpStatus httpStatus;
    private final String details;
    private final ResponseSeverity responseSeverity;
}
