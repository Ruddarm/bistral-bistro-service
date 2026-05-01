package com.bistral.app.bistral_bistro_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchContextDto {

    private UUID branchId;
    private String branchName;

}
