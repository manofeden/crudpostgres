package com.mycompany.crudpostgres.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/***
 * @author Leonid Ivanov
 */
@Configuration
@ComponentScan("com.mycompany")
@EnableWebMvc
public class MyConfig {

	private Properties loadConfig() {
		try {
			Properties properties = new Properties();
			properties.load(new InputStreamReader(new FileInputStream(com.mycompany.crudpostgres.Astat.CONFIG_FILE_PATH), System.getProperty("file.encoding", "UTF-8")));
			return properties;
		} catch (IOException | NullPointerException e) {
			System.out.println("Warning: config file is not loaded...");
		}
		return null;
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");

		Properties config = loadConfig();
		if (config == null) {
			dataSource.setUrl("jdbc:postgresql://localhost:5432/polyclinic");
			dataSource.setUsername("admin");
			dataSource.setPassword("admin");
		} else {
			dataSource.setUrl(config.getProperty("url"));
			dataSource.setUsername(config.getProperty("username"));
			dataSource.setPassword(config.getProperty("password"));
		}

		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());

		return jdbcTemplate;
	}

}
