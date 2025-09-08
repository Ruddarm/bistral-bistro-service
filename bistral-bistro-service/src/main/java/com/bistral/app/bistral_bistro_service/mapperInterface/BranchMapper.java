package com.bistral.app.bistral_bistro_service.mapperInterface;

import com.bistral.app.bistral_bistro_service.dtos.BranchRequest;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchResponse toBranchResponse(BranchEntity branchEntity);
    BranchEntity toBranchEntity(BranchRequest branchRequest);
}

