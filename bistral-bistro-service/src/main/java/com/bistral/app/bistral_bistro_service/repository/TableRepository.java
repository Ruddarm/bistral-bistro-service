package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, UUID> {


    @Query("""
                SELECT new com.bistral.app.bistral_bistro_service.dtos.TableResponse(
                    t.tableId,t.tableNo,t.zone.zoneId
                )
                FROM TableEntity t
                WHERE t.branch.branchId = :branchId
                and t.zone.zoneId = :zoneId
            """)
    List<TableResponse> findByBranch_BranchId(UUID branchId,UUID zoneId);
}
