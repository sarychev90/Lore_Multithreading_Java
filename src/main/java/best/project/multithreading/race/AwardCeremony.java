package best.project.multithreading.race;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AwardCeremony implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(AwardCeremony.class.getName());
    private Map<Integer, Long> raceResults;
    private CountDownLatch latch;

    public AwardCeremony(Map<Integer, Long> raceResults, CountDownLatch latch) {
        this.raceResults = raceResults;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Awards ceremony starting...");
            Thread.sleep(1000);
            for (Map.Entry<Integer, Long> raceParticipant : raceResults.entrySet()) {
                LOGGER.info(String.format("Car %d - finished with time: %d", raceParticipant.getKey(), raceParticipant.getValue()));
            }
            LOGGER.info("Awards ceremony finished");
            latch.countDown();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Error during the awards ceremony", e);
        }
    }
}
