package com.bistral.app.bistral_bistro_service.dtos;


import lombok.Builder;
import lombok.Data;
import org.apache.commons.text.translate.UnicodeUnescaper;

import java.util.UUID;

@Builder
@Data
public class ZoneRequest {

    private UUID branchId;
    private  String zoneName;

}
