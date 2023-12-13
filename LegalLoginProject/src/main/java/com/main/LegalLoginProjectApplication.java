package com.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title = "Legal Reporting System ", version = "1.0",description = "Legal Reporting System in this documentation we have login API of Super Admin , Firm , Lawyer"))
public class LegalLoginProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegalLoginProjectApplication.class, args);
        System.out.println("Legal Login is Running");
    }


}
