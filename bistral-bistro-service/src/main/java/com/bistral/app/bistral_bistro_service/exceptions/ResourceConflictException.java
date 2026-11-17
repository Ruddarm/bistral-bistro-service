package com.bistral.app.bistral_bistro_service.exceptions;

import com.bistral.app.bistral_bistro_service.enums.Resource;
import com.bistral.app.bistral_bistro_service.enums.responseCode.ApiResponseCode;
import com.bistral.app.bistral_bistro_service.enums.responseCode.ResponseSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public class ResourceConflictException extends BaseException {

    private Resource resource;

    public ResourceConflictException(Resource resource, ApiResponseCode apiResponseCode) {
        super(apiResponseCode);
        this.resource = resource;

    }

    public static ResourceConflictException from(Resource resource, ApiResponseCode apiResponseCode) {
        return new ResourceConflictException(resource, apiResponseCode);
    }

}
