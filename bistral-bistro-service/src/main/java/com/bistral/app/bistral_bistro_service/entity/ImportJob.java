package com.bistral.app.bistral_bistro_service.entity;


import com.bistral.app.bistral_bistro_service.entity.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "importJob")
public class ImportJob {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobId;
    @Column(nullable = false, columnDefinition = "Varchar(30) default 'PENDING' ")
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;
    @Column(nullable = false)
    private int rowProcess;
    @Column(nullable = false)
    private int failedRows;
    @Column(nullable = false)
    private int successRows;
}
