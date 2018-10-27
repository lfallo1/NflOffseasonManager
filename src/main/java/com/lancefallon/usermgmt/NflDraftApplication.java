package com.lancefallon.usermgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring boot - Entry point
 *
 * @author lancefallon
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.lancefallon.usermgmt")
public class NflDraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(NflDraftApplication.class, args);
    }
}
