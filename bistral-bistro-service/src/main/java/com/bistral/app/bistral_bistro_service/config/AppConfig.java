package com.bistral.app.bistral_bistro_service.config;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.UUID;


@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final EntityManager entityManager;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return  mapper;
    }

    @Bean
    public JPAQueryFactory queryFactory(){
        return  new JPAQueryFactory(entityManager);
    }


    @Bean
    public AuditorAware<UUID> auditorAware(){
        return ()->
                java.util.Optional.of((UUID.randomUUID()));
    }

}
