package com.bistral.app.bistral_bistro_service.repositoryTest;


import com.bistral.app.bistral_bistro_service.config.TestContainer;
import com.bistral.app.bistral_bistro_service.dtos.BistroRequest;
import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.BranchRepository;
import com.bistral.app.bistral_bistro_service.service.BistroService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "com.bistral.app.bistral_bistro_service.entity")
@EnableJpaRepositories(basePackages = "com.bistral.app.bistral_bistro_service.repository")
public class BranchRepositoryUnitTest {

    @Autowired
    private BistroRepository bistroRepository;

    @Autowired
    private BranchRepository branchRepository;


    @Test
    void contextLoads() {
    }
    @Test
    public void testFindByBranchIdAnd_bistroId_whenBothAreValid() {
        BistroEntity bistroEntity = BistroEntity.builder().bistroName("local-bistro")
                .userId(UUID.randomUUID()).build();
        bistroEntity = bistroRepository.save(bistroEntity);
        BranchEntity branchEntity = BranchEntity
                .builder()
                .branchName("local-bistro-thane")
                .bistro(bistroEntity)
                .build();
        branchEntity = branchRepository.save(branchEntity);
        BranchEntity branch = branchRepository.findByBranchIdAndBistroId(branchEntity.getBranchId(), bistroEntity.getBistroId())
                .orElse(null);
        Assertions.assertThat(branch)
                .isNotNull();
        Assertions
                .assertThat(branch.getBistro().getBistroId())
                .isEqualTo(bistroEntity.getBistroId());
        Assertions.assertThat(branch.getBranchId())
                .isEqualTo(branchEntity.getBranchId());
        ;

    }
}
