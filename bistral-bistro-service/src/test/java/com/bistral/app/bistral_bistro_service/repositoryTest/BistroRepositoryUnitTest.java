package com.bistral.app.bistral_bistro_service.repositoryTest;

import com.bistral.app.bistral_bistro_service.config.TestContainer;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BistroRepositoryUnitTest {
    @Autowired
    private BistroRepository bistroRepository;

    @Test
    public  void load(){

    }


}
