package io.ljunggren.tracking.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application extends SpringBootServletInitializer {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootServletInitializer.class, args);
    }

}
