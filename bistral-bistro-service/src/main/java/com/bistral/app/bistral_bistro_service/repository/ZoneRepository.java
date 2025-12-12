package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ZoneRepository extends JpaRepository<BranchZoneEntity, UUID> {

    @Query("Select  z from BranchZoneEntity z where z.branch.branchId = :branchId")
     List<BranchZoneEntity> findAllByBranch(UUID branchId);
}
