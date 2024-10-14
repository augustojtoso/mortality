package com.humanit.recruiting.mortality.application;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi mortalityApi() {
        return GroupedOpenApi.builder()
                .group("Mortality")
                .pathsToMatch("/mortalityrates/**")
                .build();
    }

    @Bean
    public CommandLineRunner commandLineRunner(Environment environment) {
        return args -> {
            String port = environment.getProperty("server.port", "8080");
            System.out.println("Swagger UI: http://localhost:" + port + "/swagger-ui.html");
        };
    }
}
