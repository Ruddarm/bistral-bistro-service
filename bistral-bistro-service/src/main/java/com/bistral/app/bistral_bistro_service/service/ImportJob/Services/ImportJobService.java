package com.bistral.app.bistral_bistro_service.service.ImportJob.Services;


import com.bistral.app.bistral_bistro_service.entity.ImportJob;
import com.bistral.app.bistral_bistro_service.repository.ImportJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ImportJobService {

    private final ImportJobRepository importJobRepository;

    public ImportJob createImportJob(ImportJob importJob){
        return importJobRepository.save(importJob);
    }

}
