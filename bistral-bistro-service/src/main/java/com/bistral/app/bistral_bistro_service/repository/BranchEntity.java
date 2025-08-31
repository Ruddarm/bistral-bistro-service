package com.bistral.app.bistral_bistro_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchEntity extends JpaRepository<BranchEntity, UUID> {


}
