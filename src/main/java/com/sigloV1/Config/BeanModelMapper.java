package com.sigloV1.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanModelMapper {

    @Bean
    public ModelMapper ModelMapper(){
        return new ModelMapper();
    }
}
