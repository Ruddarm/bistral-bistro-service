package com.bistral.app.bistral_bistro_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class ResourceNotFoundException extends RuntimeException {
    private  String resourceName;
    private  int code;

    public ResourceNotFoundException(String resourceName, int code, String message) {
        super(message);
        this.resourceName = resourceName;
        this.code = code;
    }

    public ResourceNotFoundException(String resourceName, String message){
        super(message);
        this.resourceName=resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getCode() {
        return code;
    }
}
