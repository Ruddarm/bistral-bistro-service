package com.bistral.app.bistral_bistro_service.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainer {

    @Bean
    @ServiceConnection
    static  PostgreSQLContainer<?> postgreSQLContainer(){
        return  new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
                .withDatabaseName("testDb")
                .withPassword("testpswd")
                .withUsername("testUser")
                .withEnv("TZ", "UTC")
                .withEnv("PGTZ", "UTC");

    }
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }


}
