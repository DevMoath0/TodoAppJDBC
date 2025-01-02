package com.moath.todoappjdbc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {


    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();

        String devUrl = "http://localhost:8080";
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setName("Moath");

        Info info = new Info()
                .title("Todo API")
                .version("1.0")
                .contact(contact)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        return new OpenAPI().info(info).servers(List.of(devServer));

    }
}