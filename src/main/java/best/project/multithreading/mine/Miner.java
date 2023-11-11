package best.project.multithreading.mine;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Miner implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Miner.class.getName());
	private Mine mine;
	private CountDownLatch latch;
	private int coalMinedAmount;
	private int minerNumber;

	public Miner(Mine mine, CountDownLatch latch, int minerNumber) {
		this.mine = mine;
		this.latch = latch;
		this.minerNumber = minerNumber;
	}

	@Override
	public void run() {
		while(mine.getCoalDepositsAmount() > 0) {
			//LOGGER.log(Level.INFO, "Start mining...");
			int coalMinedInAmount = mine.getDigUpNorm();
			coalMinedAmount = coalMinedAmount + coalMinedInAmount;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.log(Level.WARNING, "Thread.sleep exception", e);
			}
			//LOGGER.log(Level.INFO, "Finished mining...");
			//LOGGER.log(Level.INFO, "Coal mined IN amount: {0}", coalMinedInAmount);
		}
		LOGGER.log(Level.INFO, String.format("Coal total mined IN amount for miner %d is: %d", minerNumber, coalMinedAmount));
		latch.countDown();
	}

	public int getCoalMinedAmount() {
		return coalMinedAmount;
	}
}
