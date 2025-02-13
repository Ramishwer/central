package com.goev.central.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {
    @Bean(destroyMethod = "shutdown", name = "executorService")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(30);
    }
}
