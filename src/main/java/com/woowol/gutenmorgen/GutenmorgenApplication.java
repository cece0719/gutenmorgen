package com.woowol.gutenmorgen;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.IOException;

@SpringBootApplication
public class GutenmorgenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GutenmorgenApplication.class, args);
    }

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        return new PropertyPlaceholderConfigurer() {{
            setLocations((new PathMatchingResourcePatternResolver()).getResources("classpath:/properties/**/*.properties"));
        }};
    }

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }
}
