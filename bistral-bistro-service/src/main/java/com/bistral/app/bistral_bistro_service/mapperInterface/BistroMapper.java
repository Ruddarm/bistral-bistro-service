package com.bistral.app.bistral_bistro_service.mapperInterface;

import com.bistral.app.bistral_bistro_service.dtos.BistroRequest;
import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BistroMapper {
    BistroResponse toResponse(BistroEntity bistroEntity);
    BistroEntity toEntity(BistroRequest bistroRequest);
}
