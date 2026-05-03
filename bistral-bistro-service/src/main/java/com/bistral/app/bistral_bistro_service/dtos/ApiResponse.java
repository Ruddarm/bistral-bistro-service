package com.bistral.app.bistral_auth_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
@Data
public class ApiResponse<T> {
    String message;
    Boolean isError = false;
    T data;
    Map<String,Object> meta = new HashMap<>();
}
