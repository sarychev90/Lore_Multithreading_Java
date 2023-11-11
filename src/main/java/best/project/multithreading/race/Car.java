package best.project.multithreading.race;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Car.class.getName());
    private Finish finish;
    private int carNumber;

    public Car(Finish finish, int carNumber) {
        this.finish = finish;
        this.carNumber = carNumber;
    }

    @Override
    public void run() {
        try {
            LOGGER.info(String.format("Car %d start race...", carNumber));
            long raceTime = 1000 + new Random().nextInt(1000);
            LOGGER.info(String.format("Race time for car %d is: %d", carNumber, raceTime));
            Thread.sleep(raceTime);
            LOGGER.info(String.format("Car %d finished race.", carNumber));
            finish.setRaceResult(carNumber);
        } catch (InterruptedException | BrokenBarrierException e) {
            LOGGER.log(Level.WARNING, String.format("Exception for finish achieving %d", carNumber), e);
        }
    }
}
