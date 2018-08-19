package com.kotlarz.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("com.kotlarz")
@EnableScheduling
public class Application {
    public static final String APP_URL = "crashed";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
