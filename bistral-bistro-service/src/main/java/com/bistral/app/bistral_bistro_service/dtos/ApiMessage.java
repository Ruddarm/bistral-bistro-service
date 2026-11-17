package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.enums.responseCode.ApiResponseCode;
import com.bistral.app.bistral_bistro_service.enums.responseCode.ResponseSeverity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiMessage {
    ResponseSeverity responseSeverity;
    String code;
    String title;
    String message;
    Map<String,Object> params;

    public static ApiMessage of(ApiResponseCode responseCode){
        return ApiMessage.builder()
                .responseSeverity(responseCode.getResponseSeverity())
                .message(responseCode.getDetails())
                .params(Map.of())
                .code(responseCode.getCode())
                .build();
    }
}
