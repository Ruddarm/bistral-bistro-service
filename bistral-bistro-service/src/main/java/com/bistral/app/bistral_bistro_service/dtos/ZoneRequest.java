package com.bistral.app.bistral_bistro_service.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.text.translate.UnicodeUnescaper;

import java.util.UUID;

@Builder
@Data
public class ZoneRequest {

    @NotBlank
    @NotEmpty
    private  String zoneName;

}
