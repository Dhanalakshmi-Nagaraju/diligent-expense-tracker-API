package com.diligent.expense_tracker.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseTrackerOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker API")
                        .description("REST API for managing personal expenses")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Dhanalakshmi N"))
                        .license(new License()
                                .name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}