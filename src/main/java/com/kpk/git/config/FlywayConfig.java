package com.kpk.git.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
	
	@Bean
	public void flyway(DataSource dataSource){
		Flyway.configure().dataSource(dataSource).load().migrate();
	}
}
