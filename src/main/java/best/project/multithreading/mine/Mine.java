package best.project.multithreading.mine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mine {
	
	private static final Logger LOGGER = Logger.getLogger(Mine.class.getName());
	private int digUpNorm;
	private volatile int coalDepositsAmount;
	
	public Mine(int coalDepositsAmount, int digUpNorm) {
		this.digUpNorm = digUpNorm;
		this.coalDepositsAmount = coalDepositsAmount;
	}

	public synchronized int getDigUpNorm() {
		int coalMinedOutAmount = 0;
		if(coalDepositsAmount > 0) {
			if((coalDepositsAmount - digUpNorm) >= 0) {
				coalDepositsAmount = coalDepositsAmount - digUpNorm;
				coalMinedOutAmount = digUpNorm;
			} else {
				coalMinedOutAmount = coalDepositsAmount;
				coalDepositsAmount = 0;
			}
		}
		LOGGER.log(Level.INFO, "Coal mined OUT amount: {0}", coalMinedOutAmount);
		return coalMinedOutAmount;
	}

	public int getCoalDepositsAmount() {
		return coalDepositsAmount;
	}

}
