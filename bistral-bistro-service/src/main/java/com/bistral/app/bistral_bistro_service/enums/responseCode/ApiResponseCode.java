package com.bistral.app.bistral_bistro_service.enums.responseCode;

import org.springframework.http.HttpStatus;

public interface ApiResponseCode {

    public String getCode();
    public String getSlug();
    public String getTitle();
    public HttpStatus getHttpStatus();
    public String getDetails();
    public ResponseSeverity getResponseSeverity();

}
