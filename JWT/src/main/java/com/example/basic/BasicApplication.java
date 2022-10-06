package com.example.basic;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("JWT Authentication")
                        .description("Danh sách các chức năng của ứng dụng")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Bùi Hiên")
                                .email("hien@techmaster.vn"))
                );
    }
}
