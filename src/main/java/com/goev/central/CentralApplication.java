package com.goev.central;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.goev.lib", "com.goev.central"})
@Slf4j
@EnableScheduling
public class CentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralApplication.class, args);
    }

}
