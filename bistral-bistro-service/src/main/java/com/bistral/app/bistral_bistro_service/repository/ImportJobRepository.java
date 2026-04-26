package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.ImportJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImportJobRepository extends JpaRepository<ImportJob, UUID> {

}
