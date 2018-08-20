package com.mycompany.crudpostgres.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class StatData {
	public int counter;
	public final int COUNTER_MAX_VALUE = 100;
	public final String CONFIG_FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "config.ini";
}
