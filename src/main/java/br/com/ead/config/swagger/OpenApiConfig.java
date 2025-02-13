package br.com.ead.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API do Curso EAD", version = "1.0", description = "Documentação da API para o gerenciamento de cursos EAD"))
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("curso-ead-api")
                .packagesToScan("br.com.ead.controller")
                .build();
    }
}
