package best.project.multithreading.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class StorageTest {
    private int itemProductionCycle = 3;
    @Test
    void storageTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicInteger itemQuantity = new AtomicInteger(itemProductionCycle+1);
        Storage storage = new Storage();
        Thread producerThread = new Thread(new Producer(storage, itemQuantity, latch));
        Thread consumerThread = new Thread(new Consumer(storage, itemQuantity, latch));
        producerThread.start();
        consumerThread.start();
        latch.await();
        assertEquals(0, itemQuantity.get());
    }

}
