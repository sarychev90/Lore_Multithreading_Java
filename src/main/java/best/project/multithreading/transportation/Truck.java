package best.project.multithreading.transportation;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Truck implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Truck.class.getName());

    private Exchanger<Cargo> exchanger;
    private Cargo cargo;
    private int truckNumber;
    private CountDownLatch latch;

    public Truck(Exchanger<Cargo> exchanger, Cargo cargo, int truckNumber, CountDownLatch latch) {
        this.exchanger = exchanger;
        this.cargo = cargo;
        this.truckNumber = truckNumber;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            LOGGER.info(String.format("Truck %d start moving with cargo %s...", truckNumber, cargo.getCargoName()));
            Thread.sleep(1000 + new Random().nextInt(1000));
            LOGGER.info(String.format("Truck %d waiting for cargo exchange...", truckNumber));
            cargo = exchanger.exchange(cargo);
            LOGGER.info(String.format("Truck %d loaded cargo %s", truckNumber, cargo.getCargoName()));
            LOGGER.info(String.format("Truck %d finished moving.", truckNumber));
            latch.countDown();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, String.format("Exception for truck %d transportation", truckNumber), e);
        }
    }

    public Cargo getCargo() {
        return cargo;
    }
}
