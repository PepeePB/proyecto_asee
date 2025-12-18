package com.musicfly.backend.estadisticas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // 1. Título del API
                        .title("Musicfly - Microservicio de Estadisticas")
                        // 2. Versión
                        .version("1.0.0")
                        // 3. Descripción
                        .description("API para gestionar las estadisticas"));
    }
}
