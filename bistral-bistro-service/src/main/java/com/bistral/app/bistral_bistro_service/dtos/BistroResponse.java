package com.bistral.app.bistral_bistro_service.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BistroResponse {
    private UUID bistroId;
    private  String bistroName;
    
}
