package com.bistral.app.bistral_bistro_service.repositoryTest;

import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BistroRepositoryUnitTest {
    @Autowired
    private BistroRepository bistroRepository;


}
