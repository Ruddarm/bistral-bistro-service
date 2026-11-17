package com.bistral.app.bistral_bistro_service.entity;

import com.bistral.app.bistral_bistro_service.enums.responseCode.ResponseSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ExceptionResponse {
    private  String code;
    private  String slug;
    private  String title;
    private  String message;
    private ResponseSeverity responseSeverity;
}
