package com.override.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void runNativeSql() {
		ClassPathResource resource = new ClassPathResource("scripts/init_db.sql");
		try(Connection connection = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(connection, resource);
		} catch (SQLException | ScriptException e) {
			//LOG
		}
	}

}
