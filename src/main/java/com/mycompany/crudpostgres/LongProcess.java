package com.mycompany.crudpostgres;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Leonid Ivanov
 */
public class LongProcess {
	private Thread longProcess;
	private static final String PROCESS_NAME = "MyLongProcess";

	public LongProcess() {
		longProcess = new Thread(() -> {
			try {
				System.out.println("Start of the process...");
				Thread.sleep(60000);
				Astat.counter = 0;
				System.out.println("End of the process.");
			} catch (InterruptedException ex) {
				System.out.println("<<< Process interrupted ! >>>");
				Logger.getLogger(LongProcess.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		longProcess.setName(PROCESS_NAME);
		longProcess.setPriority(Thread.NORM_PRIORITY - 2);
	}

	public void start() {
		longProcess.start();
	}

	public static void checkLongProcess() {
		if (Astat.counter == Astat.COUNTER_MAX_VALUE) {
			LongProcess process = new LongProcess();
			process.start();
		}
	}

	public static boolean isLongProcessWork() {
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
