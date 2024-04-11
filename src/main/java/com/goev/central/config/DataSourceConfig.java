package com.goev.central.config;


import com.goev.central.dto.SystemCredentialDto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(SystemCredentialDto systemCredentialDto) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(Integer.parseInt(systemCredentialDto.getMysqlPoolSize()));
        config.setUsername(systemCredentialDto.getMysqlUserName());
        config.setJdbcUrl("jdbc:mysql://" + systemCredentialDto.getMysqlHostName() + ":" + systemCredentialDto.getMysqlPort() + "/" + systemCredentialDto.getMysqlDatabase() + "?serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");
        config.setPassword(systemCredentialDto.getMysqlPassword());
        return new HikariDataSource(config);
    }
}
