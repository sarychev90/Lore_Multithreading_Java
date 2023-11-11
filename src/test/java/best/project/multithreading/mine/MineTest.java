package best.project.multithreading.mine;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MineTest {
	private static final Logger LOGGER = Logger.getLogger(MineTest.class.getName());
	private List<Miner> miners;
	private int minerOrderNumber = 0;
	private int numberOfThreads = 7;
	private int minerNumber = 1;
	private int coalDepositsAmount = 100;
	private int digUpNorm = 3;
	CountDownLatch latch = new CountDownLatch(numberOfThreads);
	@Test
	void mineTest() throws InterruptedException {
		generateMiners();
		Stream.generate(()-> new Thread(getMiner())).limit(numberOfThreads).forEach(Thread::start);
		latch.await();
		LOGGER.log(Level.INFO, String.format("Coal total mined my miners %d", getMinedCoalAmount()));
		assertEquals(coalDepositsAmount, getMinedCoalAmount());
	}

	private void generateMiners(){
		Mine mine = new Mine(coalDepositsAmount, digUpNorm);
		miners = Stream.generate(()-> new Miner(mine, latch, getMinerNumber())).limit(numberOfThreads).collect(Collectors.toList());
	}

	private Miner getMiner(){
		Miner miner = miners.get(minerOrderNumber);
		minerOrderNumber++;
		return miner;
	}

	private int getMinerNumber(){
		return minerNumber++;
	}

	private int getMinedCoalAmount(){
		return miners.stream().mapToInt(Miner::getCoalMinedAmount).sum();
	}
}
