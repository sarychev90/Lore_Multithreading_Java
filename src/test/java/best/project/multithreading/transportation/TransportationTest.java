package best.project.multithreading.transportation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

import org.junit.jupiter.api.Test;

public class TransportationTest {

    @Test
    void transportationTest() throws InterruptedException {
        String cargo1Name = "Cargo ¹–1";
        String cargo2Name = "Cargo ¹–2";
        CountDownLatch latch = new CountDownLatch(2);
        Exchanger<Cargo> exchanger = new Exchanger<>();
        Cargo cargo1 = new Cargo(cargo1Name);
        Cargo cargo2 = new Cargo(cargo2Name);
        Truck truck1 = new Truck(exchanger, cargo2, 1, latch);
        Truck truck2 = new Truck(exchanger, cargo1, 2, latch);
        Thread truck1Thread = new Thread(truck1);
        Thread truck2Thread = new Thread(truck2);
        assertEquals(cargo1Name, truck2.getCargo().getCargoName());
        assertEquals(cargo2Name, truck1.getCargo().getCargoName());
        truck1Thread.start();
        truck2Thread.start();
        latch.await();
        assertEquals(cargo1Name, truck1.getCargo().getCargoName());
        assertEquals(cargo2Name, truck2.getCargo().getCargoName());
    }
}
