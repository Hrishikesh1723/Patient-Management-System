package com.example.PatientManagementSystem.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * This class defines the configuration for the OpenAPI 3.0 documentation
 * for the Patient Management System, enabling the generation of API docs
 * for the application.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the application.
     *
     * @return an OpenAPI object containing metadata for the API.
     */
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Patient Management System API")
                        .description("API documentation for the Patient Management System")
                        .version("1.0.0")
                );

    }
    @Bean
    public OpenApiCustomizer removePageSchema() {
        return openApi -> {
            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                // Remove specific pageable-related schemas
                openApi.getComponents().getSchemas().remove("Page");
                openApi.getComponents().getSchemas().remove("PageReport");
                openApi.getComponents().getSchemas().remove("PageableObject");
                openApi.getComponents().getSchemas().remove("PagePatient");
                openApi.getComponents().getSchemas().remove("PageAppointment");
                openApi.getComponents().getSchemas().remove("PageDoctor");
                openApi.getComponents().getSchemas().remove("PageDepartment");
                openApi.getComponents().getSchemas().remove("SortObject");
            }
        };
    }

}
