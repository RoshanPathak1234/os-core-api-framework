package com.os.framework.os_core_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@OpenAPIDefinition(
		info = @Info(
				title = "Operating System Core API",
				version = "1.0.0",
				description = "A comprehensive REST API framework implementing key Operating System algorithms including CPU scheduling, memory management, disk scheduling, and more.",
				contact = @Contact(
						name = "OS Framework Dev Team",
						email = "roshanpathak659@gmail.com"
				),
				license = @License(
						name = "Github",
						url = "github"
				)
		)
)
@SpringBootApplication
public class OsCoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsCoreApiApplication.class, args);
		log.info("🚀 OS Core API is up and running at http://localhost:8080/swagger-ui.html");
		System.out.println("Application started.");
	}
}
