package com.bistral.app.bistral_bistro_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BistroContextDto {
    private UUID bistroId;
    private String bistroName;
    private List<BranchContextDto> branches;
}
