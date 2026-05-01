package com.bistral.app.bistral_bistro_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BistroContextRequestDto {

    List<UUID> bistroIds;
    List<UUID> branchIds;

}
