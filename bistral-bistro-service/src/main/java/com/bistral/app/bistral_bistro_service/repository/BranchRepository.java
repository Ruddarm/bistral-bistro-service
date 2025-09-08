package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {

     Optional<BranchEntity> findByBranchIdAndBistro_bistroId(UUID branchId, UUID bistroId);
}
