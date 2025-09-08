package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {

    @Query("""
               SELECT br
               FROM BranchEntity br
               JOIN FETCH br.bistro b
               WHERE br.branchId = :branchId
                 AND b.bistroId = :bistroId
            """)
    Optional<BranchEntity> findByBranchIdAndBistroId(
            @Param("branchId") UUID branchId,
            @Param("bistroId") UUID bistroId
    );
}
