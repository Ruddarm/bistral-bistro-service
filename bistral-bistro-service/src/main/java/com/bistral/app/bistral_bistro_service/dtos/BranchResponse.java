package com.bistral.app.bistral_bistro_service.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BranchResponse {
    private UUID bistroId;
    private UUID branchId;
    private String branchName;
}
