package com.mycompany.crudpostgres;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.crudpostgres.service.StatData;

@Component
@Scope("singleton")
public class LongProcess {
	private Thread longProcess;
	private static final String PROCESS_NAME = "MyLongProcess";

	@Autowired
	StatData stat;

	public void startProcess() {
		longProcess = new Thread(() -> {
			try {
				System.out.println("Start of the process...");
				Thread.sleep(60000);
				stat.clearCounter();
				System.out.println("End of the process.");
			} catch (InterruptedException ex) {
				System.out.println("<<< Process interrupted ! >>>");
				Logger.getLogger(LongProcess.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		longProcess.setName(PROCESS_NAME);
		longProcess.setPriority(Thread.NORM_PRIORITY - 2);
		longProcess.start();
	}

	public void checkLongProcess() {
		if (stat.getCounter() == stat.COUNTER_MAX_VALUE) {
			startProcess();
		}
	}

	public boolean isLongProcessWork() {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

		Iterator<Thread> iterator = threadSet.iterator();
		while (iterator.hasNext()) {
			Thread thr = iterator.next();
			if (thr.getName().equals(PROCESS_NAME)) {
				return true;
			}
		}

		return false;
	}

}
