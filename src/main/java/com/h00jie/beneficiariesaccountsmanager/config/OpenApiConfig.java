package com.h00jie.beneficiariesaccountsmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Beneficiaries Account Management API")
                        .version("1.0")
                        .description("API για τη διαχείριση δικαιούχων, λογαριασμών και συναλλαγών.")
                        .contact(new Contact()
                                .name("Παναγιώτης Χατζηκωνσταντίνου")
                                .email("panos.hd@gmail.com")
//                                .url("https://www.example.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
}