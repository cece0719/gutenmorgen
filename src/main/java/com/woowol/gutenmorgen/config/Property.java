package com.woowol.gutenmorgen.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
public class Property {
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        return new PropertyPlaceholderConfigurer() {{
            setLocations((new PathMatchingResourcePatternResolver()).getResources("classpath:/properties/**/*.properties"));
        }};
    }
}