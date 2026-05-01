package com.bistral.app.bistral_bistro_service.projection;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BistroBranchContextProjection {

    private UUID bistroId;
    private String bistroName;
    private UUID branchId;
    private String branchName;
}
