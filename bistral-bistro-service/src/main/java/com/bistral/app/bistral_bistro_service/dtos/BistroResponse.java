package com.bistral.app.bistral_bistro_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BistroResponse {
    private UUID bistroId;
    private  String bistroName;
    BistroResponse(UUID bistroId , String  bistroName){
        this.bistroName=bistroName;
        this.bistroId=bistroId;
    }
    private  List<BranchResponse> branchResponses = new ArrayList<>();
}
