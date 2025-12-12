package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.entity.TableEntity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ZoneResponse {

    private UUID zoneId;
    private String zoneName;
    private List<TableResponse> tableResponses = new ArrayList<>();

}
