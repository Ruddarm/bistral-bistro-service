package com.bistral.app.bistral_bistro_service.dtos;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ListingDto<T> {
    private Integer size;
    private Integer page;
    private String sortBy;
    private String sortOrder;
    private T filterDto;

}
