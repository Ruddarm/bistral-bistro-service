package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.repository.SubDomainMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Provide services for subdomain mapped to tenant or bistro.
 */
@AllArgsConstructor
@Service
public class SubDomainMappingService {


    private final SubDomainMappingRepository subDomainMappingRepository;







    /**
     * Return true if subdomain is taken by another tenant.
     * @param subdomain subdomain
     * @return true if subdomain is taken else false.
     */
    public boolean isSubDomainTaken(String subdomain){
        return subDomainMappingRepository
                .existsBySubDomainAndDeletedAtIsNull(subdomain);
    }


}
