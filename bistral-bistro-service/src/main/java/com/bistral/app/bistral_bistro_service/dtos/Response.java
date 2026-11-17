package com.bistral.app.bistral_bistro_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String traceId;
    private  T data;
    @Builder.Default
    private  List<ApiMessage> apiMessageList = new ArrayList<>();

    public Response(T data, ApiMessage message) {
        this.data = data;
        apiMessageList = new ArrayList<>();
        this.apiMessageList.add(message);
    }

    public static <T> Response<T> from(T data, ApiMessage message) {
        return new Response<T>(data, message);
    }
}
