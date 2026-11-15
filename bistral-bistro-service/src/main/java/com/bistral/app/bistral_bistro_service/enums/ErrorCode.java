package com.bistral.app.bistral_bistro_service.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("USER_001", "userNotFound", "User Not Found", HttpStatus.NOT_FOUND, "User not found with given authentication", ExceptionSeverity.ERROR),
    SUBDOMAIN_TAKEN("SUBDOMAIN_001", "subdomainTaken", "Subdomain is taken", HttpStatus.CONFLICT, "Subdomain is already taken by another tenant", ExceptionSeverity.ERROR);

    private final String code;
    private final String slug;
    private final String title;
    private final HttpStatus httpStatus;
    private final String detail;
    private final ExceptionSeverity exceptionSeverity;
}
