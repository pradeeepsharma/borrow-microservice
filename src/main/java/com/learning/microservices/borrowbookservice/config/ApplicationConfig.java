package com.learning.microservices.borrowbookservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@Getter
public class ApplicationConfig {

    @Value("${api.url.book-service}")
    private String bookServiceApi;
    @Value("${api.url.user-service}")
    private String userServiceApi;


}
