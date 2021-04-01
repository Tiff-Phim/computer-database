package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.service", "com.excilys.cdb.servlet",
		"com.excilys.cdb.controller", "com.excilys.cdb.mapper" })
public class SpringConfig extends AbstractContextLoaderInitializer {

	private static final String PROP_FILE_NAME = "/config/db.properties";

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}

	@Bean
	public DataSource getDataSource() {
		return new HikariDataSource(new HikariConfig(PROP_FILE_NAME));
	}

}