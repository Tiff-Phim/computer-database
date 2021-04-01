package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.service", "com.excilys.cdb.servlet",
		"com.excilys.cdb.controller", "com.excilys.cdb.mapper" })
public class SpringWebConfig implements WebApplicationInitializer {

	private static final String PROP_FILE_NAME = "/config/db.properties";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	    context.register(SpringWebConfig.class);
	    context.setServletContext(servletContext);
	}
	
	@Bean
	public DataSource getDataSource() {
		return new HikariDataSource(new HikariConfig(PROP_FILE_NAME));
	}

}
