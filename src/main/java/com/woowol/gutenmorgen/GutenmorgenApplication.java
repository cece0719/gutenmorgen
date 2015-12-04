package com.woowol.gutenmorgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@SpringBootApplication
@PropertySource({
        "classpath:properties/application.properties",
        "classpath:properties/application-${spring.profiles.active}.properties"
})
public class GutenmorgenApplication {
    public static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(GutenmorgenApplication.class, args);
    }

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }
}
