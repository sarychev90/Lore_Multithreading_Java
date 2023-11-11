package best.project.multithreading.race;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RaceTest {
	private int numberOfCars = 3;
	private int carNumber = 1;

	@Test
	void raceTest() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		Map<Integer, Long> raceResults = new ConcurrentHashMap<>();
		Finish finish = new Finish(numberOfCars, raceResults, new AwardCeremony(raceResults, latch));
		Stream.generate(()-> new Thread(new Car(finish, getCarNumber()))).limit(numberOfCars).forEach(Thread::start);
		latch.await();
		assertEquals(numberOfCars, raceResults.size());
	}

	private int getCarNumber(){
		return carNumber++;
	}

}
