package com.fulfillment.mqintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fulfillment.mqintegration")
public class MqIntegrationApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MqIntegrationApplication.class, args);
    }
    
}
