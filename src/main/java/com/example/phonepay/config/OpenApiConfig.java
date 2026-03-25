package com.example.phonepay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PhonePe Clone API",
                version = "v1",
                description = "APIs for wallet management and money transfer",
                contact = @Contact(name = "PhonePay Service")
        ),
        servers = {
                @Server(url = "http://localhost:9091", description = "Local")
        }
)
public class OpenApiConfig {
}
