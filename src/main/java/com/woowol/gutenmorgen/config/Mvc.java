package com.woowol.gutenmorgen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Properties;

@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@ComponentScan(basePackages = "com.woowol.gutenmorgen", excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableWebMvc
@EnableScheduling
public class Mvc extends WebMvcConfigurerAdapter {
    @Bean
    public FreeMarkerViewResolver viewResolver() {
        return new FreeMarkerViewResolver() {{
            setContentType("text/html; charset=UTF-8");
            setSuffix(".ftl");
            setOrder(0);
        }};
    }

    @SuppressWarnings("serial")
    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        return new FreeMarkerConfigurer() {{
            setTemplateLoaderPath("/views/");
            setFreemarkerSettings(new Properties() {{
                setProperty("auto_import", "/spring.ftl as spring");
            }});
        }};
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(31556926);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler() {{
            setPoolSize(10);
        }};
    }

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}