package com.mycompany.crudpostgres.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class StatData {
	public AtomicInteger counter = new AtomicInteger(0);
	public final int COUNTER_MAX_VALUE = 100;
	public final String CONFIG_FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "config.ini";

	public void clearCounter() {
		counter.set(0);
	}

	public int getCounter() {
		return counter.get();
	}

	public void incrementCounter() {
		counter.incrementAndGet();
	}
}
