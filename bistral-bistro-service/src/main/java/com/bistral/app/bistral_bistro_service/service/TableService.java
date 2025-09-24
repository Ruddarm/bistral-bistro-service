package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.dtos.TableRequest;
import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.entity.TableEntity;
import com.bistral.app.bistral_bistro_service.mapperInterface.TableMapper;
import com.bistral.app.bistral_bistro_service.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TableService {
    private final TableRepository tableRepository;
    private final BranchService branchService;
    private final TableMapper tableMapper;

    public List<TableResponse> createTables(TableRequest tableRequest) {
        List<TableEntity> tableEntities = new ArrayList<>();
        BranchEntity branch = BranchEntity.builder().branchId(tableRequest.getBranchId()).build();
        for (int i = 0; i < tableRequest.getCount(); i++) {
            tableEntities.add(TableEntity.builder()
                    .tableNo(i)
                    .branch(branch).build());
        }
        tableRepository.saveAll(tableEntities);
        return tableEntities.stream().map(tableMapper::toTableResponse).toList();
    }
    public  List<TableResponse> getTables(UUID branchId){
        return  tableRepository.findByBranch_BranchId(branchId);
    }

}
