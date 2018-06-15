package com.mycompany.crudpostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan

@EnableAutoConfiguration
@SpringBootApplication
public class CrudPostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudPostgresApplication.class, args);
		System.out.println("*********************************** CrudPostgres STARTED SUCCESSFULLY  ***********************************");
	}
}
