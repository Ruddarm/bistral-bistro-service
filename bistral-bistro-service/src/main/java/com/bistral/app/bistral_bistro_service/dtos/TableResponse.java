package com.bistral.app.bistral_bistro_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {

    private UUID tableId;
    private int tableNo;
    private UUID zoneId;

}
