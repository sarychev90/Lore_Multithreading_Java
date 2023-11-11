package best.project.multithreading.race;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Finish extends CyclicBarrier {

    private Map<Integer, Long> raceResults;
    public Finish(int parties, Map<Integer, Long> raceResults, Runnable barrierAction) {
        super(parties, barrierAction);
        this.raceResults = raceResults;
    }

    public void setRaceResult(int carNumber) throws BrokenBarrierException, InterruptedException {
        raceResults.put(carNumber, System.nanoTime());
        await();
    }

    public Map<Integer, Long> getRaceResults() {
        return raceResults;
    }
}
