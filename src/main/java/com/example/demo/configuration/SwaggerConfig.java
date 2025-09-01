package com.example.demo.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .version("v1")
                        .title("POD")
                        .description("POD API")
                )
                .servers(
                        Arrays.asList(
                                new Server().url("http://localhost:8080").description("로컬 서버"),
                                new Server().url("https://dev-api.example.com").description("개발 서버"),
                                new Server().url("https://api.example.com").description("운영 서버")
                        )
                );

    }

    @Bean
    public GroupedOpenApi grouping(){
        String[] paths = {"/**"};
        return  GroupedOpenApi.builder()
                .group("spec")
                .pathsToMatch(paths)
                .build();
    }
}
