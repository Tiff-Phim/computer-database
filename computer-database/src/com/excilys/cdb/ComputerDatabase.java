package com.excilys.cdb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.WebConfig;
import com.excilys.cdb.controller.cli.CLIController;

public class ComputerDatabase {
	
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
		CLIController cli = context.getBean(CLIController.class);
		cli.run();
		((ConfigurableApplicationContext) context).close();
	}

}
