package com.bistral.app.bistral_bistro_service.dtos;


import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private UUID branchId;
    private String branchName;
    private int tables;
    private String Address;
}
