package com.bistral.app.bistral_bistro_service.exceptions;


import com.bistral.app.bistral_bistro_service.dtos.ErrorResponse;
import com.bistral.app.bistral_bistro_service.entity.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handelValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handelResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ErrorResponse.builder().errorMsg(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<?> handleBaseException(BaseException baseException) {
        log.error("Resource Conflict Occurred : {}", (Object) baseException.getStackTrace());
       ExceptionResponse exceptionResponse =
                ExceptionResponse.builder()
                        .title(baseException.getTitle())
                        .slug(baseException.getSlug())
                        .code(baseException.getCode())
                        .message(baseException.getDetails())
                        .responseSeverity(baseException.getResponseSeverity())
                        .build();
       log.info("Returning Response : {}",exceptionResponse);
       return ResponseEntity
               .status(baseException.getHttpStatus())
               .body(exceptionResponse);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handelGeneralException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder().errorMsg(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
