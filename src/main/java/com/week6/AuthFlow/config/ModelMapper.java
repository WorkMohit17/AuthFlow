package com.week6.AuthFlow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
