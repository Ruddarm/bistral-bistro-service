package com.bistral.app.bistral_bistro_service.dtos;


import lombok.*;

import java.awt.*;
import java.util.UUID;
import  java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BistroWithMenus {
    private String BistroName;
    private UUID BistroId;
    private  List<MenuResponse> menuResponseList;
}
