package com.bistral.app.bistral_bistro_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {
    private String orderId;
    private String product;
    private Double price;
}
