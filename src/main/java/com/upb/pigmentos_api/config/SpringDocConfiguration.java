package com.upb.pigmentos_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Pigmentos API")
                        .description("API Rest para la gestión de pigmentos inorgánicos, que contiene funcionalidades CRUD para colores, familias químicas y pigmentos.")
                     );
    }
}