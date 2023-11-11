package best.project.multithreading.restaurant;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitor implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(Visitor.class.getName());
	private Table table;
	private int visitorNumber;
	private CountDownLatch latch;
	private int moneyBudget;

	public Visitor(Table table, int visitorNumber, CountDownLatch latch, int moneyBudget) {
		this.table = table;
		this.visitorNumber = visitorNumber;
		this.latch = latch;
		this.moneyBudget = moneyBudget;
	}

	@Override
	public void run() {
		LOGGER.info(String.format("Visitor %d waiting for table...", visitorNumber));
		try {
			table.acquire();
			LOGGER.info(String.format("Visitor %d start eating...", visitorNumber));
			Thread.sleep(2000);
			LOGGER.info(String.format("Visitor %d finished eating.", visitorNumber));
			table.payOff(moneyBudget);
			moneyBudget = 0;
			latch.countDown();
			LOGGER.info(String.format("Visitor %d vacated the table!", visitorNumber));
		} catch (InterruptedException e) {
			LOGGER.log(Level.WARNING, String.format("Exception for table using for visitor %d", visitorNumber), e);
		}
	}

	public int getMoneyBudget() {
		return moneyBudget;
	}
}
