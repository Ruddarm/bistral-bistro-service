package com.bistral.app.bistral_bistro_service.mapperInterface;


import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.entity.TableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableMapper {
    TableResponse toTableResponse(TableEntity entity);

}
