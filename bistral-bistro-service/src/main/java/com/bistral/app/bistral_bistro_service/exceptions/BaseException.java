package com.bistral.app.bistral_bistro_service.exceptions;


import com.bistral.app.bistral_bistro_service.enums.responseCode.ApiResponseCode;
import com.bistral.app.bistral_bistro_service.enums.responseCode.ResponseSeverity;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@Builder
public class BaseException extends Exception {
    private String code;
    private String slug;
    private String title;
    private HttpStatus httpStatus;
    private String details;
    private ResponseSeverity responseSeverity;

    public BaseException(ApiResponseCode responseCode) {
        super(responseCode.getTitle());
        this.setCode(responseCode.getCode())
                .setCode(responseCode.getCode())
                .setDetails(responseCode.getDetails())
                .setSlug(responseCode.getSlug())
                .setTitle(responseCode.getTitle())
                .setHttpStatus(responseCode.getHttpStatus())
                .setResponseSeverity(responseCode.getResponseSeverity());
    }


    public static BaseException fromResponseCode(ApiResponseCode responseCode) {
        return BaseException
                .builder()
                .code(responseCode.getCode())
                .details(responseCode.getDetails())
                .slug(responseCode.getSlug())
                .title(responseCode.getTitle())
                .httpStatus(responseCode.getHttpStatus())
                .responseSeverity(responseCode.getResponseSeverity())
                .build();
    }


}
