package com.fleetpulse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fleetPulseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FleetPulse API")
                        .description("Industrial Equipment Monitoring and Predictive Maintenance Platform")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FleetPulse Engineering")
                                .url("https://github.com/priyansh-narang2308/fleetpulse")));
    }
}
